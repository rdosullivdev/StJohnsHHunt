package com.ros.stjohnshhunt.viewmodels

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.*
import androidx.work.*
import com.ros.stjohnshhunt.data.House
import com.ros.stjohnshhunt.data.RealtyRepository
import com.ros.stjohnshhunt.workers.SyncHousesWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

@HiltViewModel
class PropertiesViewModel @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val sharedPreferences: SharedPreferences,
    private val repository: RealtyRepository
) : ViewModel() {

    val houses: LiveData<List<House>> = repository.getHouses().asLiveData()

    init {
        refreshProperties()
        configureWorker()
    }

    private fun refreshProperties() {
        val lastRefresh: Long = sharedPreferences.getLong(LAST_REFRESH_SAVED_STATE_KEY, -1)
        Log.d("PropertiesViewModel", "refreshProperties, lastRefresh: $lastRefresh")

        if (isRefreshAllowed(lastRefresh).not()) {
            Log.d("PropertiesViewModel", "refreshProperties, isRefreshAllowed - nope!")
            return
        }

        viewModelScope.launch(context = Dispatchers.IO) {
            repository.getSearchResidential(
                bedRange = "2-3",
                bathRange = "1-2",
                minPrice = "180000",
                maxPrice = "400000"
            )
        }
        Calendar.getInstance().timeInMillis.also {
            sharedPreferences.edit().putLong(LAST_REFRESH_SAVED_STATE_KEY, it).apply()
            Log.d("PropertiesViewModel", "refreshProperties, getSearchResidential done, update time: $it")
        }
    }

    private fun configureWorker() {
        val constraints: Constraints = Constraints.Builder().apply {
            setRequiredNetworkType(NetworkType.CONNECTED)
        }.build()

        val periodicRefreshRequest = PeriodicWorkRequest.Builder(
            SyncHousesWorker::class.java,
            24, TimeUnit.HOURS,
            15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .setInitialDelay(12, TimeUnit.HOURS)
            .build()

        WorkManager.getInstance(appContext).enqueueUniquePeriodicWork(
                TAG_WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                periodicRefreshRequest)
    }

    private fun isRefreshAllowed(lastRefresh: Long): Boolean {
        if (lastRefresh == -1L) {
            return true
        }

        Calendar.getInstance().apply {
            add(Calendar.HOUR_OF_DAY, 12)
            return Date(lastRefresh).after(time)
        }
    }

    companion object {
        const val TAG_WORK_NAME = "SyncHousesWorker"
        private const val LAST_REFRESH_SAVED_STATE_KEY = "LAST_REFRESH_SAVED_STATE_KEY"
    }
}
