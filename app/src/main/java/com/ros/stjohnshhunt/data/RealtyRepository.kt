package com.ros.stjohnshhunt.data

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.ros.stjohnshhunt.REFRESH_INTERVAL_HOURS
import com.ros.stjohnshhunt.api.RealtyService
import com.ros.stjohnshhunt.db.AppDatabase
import com.ros.stjohnshhunt.db.HouseDao
import com.ros.stjohnshhunt.extensions.toHouses
import com.ros.stjohnshhunt.viewmodels.PropertiesViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import java.util.*
import javax.inject.Inject

class RealtyRepository @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val sharedPreferences: SharedPreferences,
    private val service: RealtyService,
    private val houseDao: HouseDao
) {

    suspend fun getSearchResidential(
        bedRange: String,
        bathRange: String,
        minPrice: String,
        maxPrice: String
    ): List<House>? {

//        if (isRefreshAllowed().not()) {
//            Timber.d("refreshProperties, isRefreshAllowed - nope!")
//            return null
//        }

        try {
            service.listResidential(
                curPage = "1",
                bedRange = bedRange,
                bathRange = bathRange,
                minPrice = minPrice,
                maxPrice = maxPrice)
            .toHouses(appContext)?.let {
                houseDao.insertAll(it)

                Calendar.getInstance().timeInMillis.also { time ->
                    sharedPreferences.edit().putLong(LAST_REFRESH_SAVED_STATE_KEY, time).apply()
                }

                return it
            }
        } catch (exception: Exception) {
            Timber.d(RealtyRepository::class.simpleName, exception.localizedMessage)
        }
        return null
    }

    fun getHouses() = houseDao.getHouses()

    fun getHouse(houseId: String) = houseDao.getHouse(houseId)

    private fun isRefreshAllowed(): Boolean {
        val lastRefresh: Long = sharedPreferences.getLong(LAST_REFRESH_SAVED_STATE_KEY, -1)
        Timber.d("PropertiesViewModel", "refreshProperties, lastRefresh: $lastRefresh")

        if (lastRefresh == -1L) {
            return true
        }

        Calendar.getInstance().apply {
            add(Calendar.HOUR_OF_DAY, REFRESH_INTERVAL_HOURS)
            return Date(lastRefresh).after(time)
        }
    }

    companion object {
        private const val LAST_REFRESH_SAVED_STATE_KEY = "LAST_REFRESH_SAVED_STATE_KEY"
    }
}
