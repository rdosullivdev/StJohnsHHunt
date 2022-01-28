package com.ros.stjohnshhunt.workers

import android.app.Notification
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.ros.stjohnshhunt.R
import com.ros.stjohnshhunt.data.House
import com.ros.stjohnshhunt.data.RealtyRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import android.app.NotificationManager

import android.app.NotificationChannel
import android.content.SharedPreferences
import android.os.Build
import com.ros.stjohnshhunt.DATE_FORMAT_1
import com.ros.stjohnshhunt.extensions.toReadableString
import com.ros.stjohnshhunt.viewmodels.DevViewModel
import timber.log.Timber
import java.util.*

@HiltWorker
class SyncHousesWorker @AssistedInject constructor(
    private val repository: RealtyRepository,
    private val sharedPreferences: SharedPreferences,
    @Assisted private val appContext: Context,
    @Assisted workerParams: WorkerParameters
): CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        Timber.d("doWork!")
        try {
            setForeground(getForegroundInfo())

            Timber.d("doWork - repository.getSearchResidential")
            val latestHouses = repository.getSearchResidential(
                bedRange = "2-3",
                bathRange = "1-2",
                minPrice = "180000",
                maxPrice = "400000"
            )

            val recentListings = latestHouses?.filter {
                it.isRecentListing()
            }

            val newListingsCount = recentListings?.size ?: 0
            updateLogs(newListingsCount)

            Timber.d("doWork - newListingsCount: $newListingsCount")
            val builder = NotificationCompat.Builder(appContext, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_bedroom)
                .setContentTitle("SyncHousesWorker")
                .setContentText("Recent Listings Count: $newListingsCount")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setOngoing(false)

            with(NotificationManagerCompat.from(appContext)) {
                notify(NOTIFICATION_ID + 1, builder.build())
            }

            Timber.d("doWork - Result.success()")

            Result.success()
        } catch (e: Exception) {
            Timber.e( "setForeground Exception", e)
            logError(e)
            Result.failure()
        }
    }

    override suspend fun getForegroundInfo(): ForegroundInfo {
        return ForegroundInfo(
            NOTIFICATION_ID, createNotification()
        )
    }

    private fun createNotification() : Notification {
        createNotificationChannel()

        return NotificationCompat.Builder(appContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_bedroom)
            .setContentTitle("SyncHousesWorker")
            .setContentText("Syncing...")
            .build()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            val notificationManager: NotificationManager =
                appContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun updateLogs(newListingsCount: Int) {
        val currentLogs = (sharedPreferences.getString(DevViewModel.SYNC_RUNS_STATE_KEY, "") ?: "")
            .plus("\n Sync ran at " + "${Calendar.getInstance().time.toReadableString(DATE_FORMAT_1)}, " +
                    "newListingsCount: $newListingsCount")

        sharedPreferences.edit().putString(DevViewModel.SYNC_RUNS_STATE_KEY, currentLogs).apply()
    }

    private fun logError(e: Exception) {
        val currentLogs = (sharedPreferences.getString(DevViewModel.SYNC_RUNS_STATE_KEY, "") ?: "")
            .plus("\n Sync ran at " + "${Calendar.getInstance().time.toReadableString(DATE_FORMAT_1)}, " +
                    "Exception: ${e.message}")

        sharedPreferences.edit().putString(DevViewModel.SYNC_RUNS_STATE_KEY, currentLogs).apply()
    }

    companion object {
        const val CHANNEL_ID = "SyncHousesWorker_CHANNEL_ID"
        const val CHANNEL_NAME = "SyncHousesWorker"
        const val NOTIFICATION_ID = 10001
    }
}