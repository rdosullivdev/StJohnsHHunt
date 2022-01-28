package com.ros.stjohnshhunt.viewmodels

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.*
import androidx.work.*
import com.ros.stjohnshhunt.REFRESH_INTERVAL_HOURS
import com.ros.stjohnshhunt.REFRESH_INTERVAL_MINS
import com.ros.stjohnshhunt.data.House
import com.ros.stjohnshhunt.data.RealtyRepository
import com.ros.stjohnshhunt.workers.SyncHousesWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext

@HiltViewModel
class PropertiesViewModel @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val repository: RealtyRepository
) : ViewModel() {

    val houses: LiveData<List<House>> = repository.getHouses().asLiveData()

    init {
        refreshProperties()
        configureWorker()
    }

    private fun refreshProperties() {
        viewModelScope.launch(context = Dispatchers.IO) {
            repository.getSearchResidential(
                bedRange = "2-3",
                bathRange = "1-2",
                minPrice = "180000",
                maxPrice = "400000"
            )
        }
    }

    private fun configureWorker() {

        val periodicRefreshRequest = PeriodicWorkRequest.Builder(
            SyncHousesWorker::class.java,
            REFRESH_INTERVAL_HOURS.toLong(), TimeUnit.HOURS,
            55, TimeUnit.MINUTES)
            .setInitialDelay(5, TimeUnit.MINUTES)
            .build()

        val operation = WorkManager.getInstance(appContext).enqueueUniquePeriodicWork(
                TAG_WORK_NAME,
                ExistingPeriodicWorkPolicy.REPLACE,
                periodicRefreshRequest)

        Timber.d("configureWorker, operation: $operation")
    }

    companion object {
        const val TAG_WORK_NAME = "SyncHousesWorker"
    }
}
