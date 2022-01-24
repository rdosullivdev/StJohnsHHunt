package com.ros.stjohnshhunt.data

import android.content.Context
import android.util.Log
import com.ros.stjohnshhunt.api.RealtyService
import com.ros.stjohnshhunt.db.AppDatabase
import com.ros.stjohnshhunt.db.HouseDao
import com.ros.stjohnshhunt.extensions.toHouses
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class RealtyRepository @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val service: RealtyService,
    private val houseDao: HouseDao
) {

    suspend fun getSearchResidential(
        bedRange: String,
        bathRange: String,
        minPrice: String,
        maxPrice: String
    ): List<House>? {
        try {
            service.listResidential(
                curPage = "1",
                bedRange = bedRange,
                bathRange = bathRange,
                minPrice = minPrice,
                maxPrice = maxPrice)
            .toHouses(appContext)?.let {
                houseDao.insertAll(it)
                return it
            }
        } catch (exception: Exception) {
            Log.d(RealtyRepository::class.simpleName, exception.localizedMessage)
        }
        return null
    }

    fun getHouses() = houseDao.getHouses()

    fun getHouse(houseId: String) = houseDao.getHouse(houseId)
}
