package com.ros.stjohnshhunt.data

import android.util.Log
import com.ros.stjohnshhunt.api.RealtyService
import com.ros.stjohnshhunt.db.AppDatabase
import com.ros.stjohnshhunt.db.HouseDao
import com.ros.stjohnshhunt.extensions.toHouses
import javax.inject.Inject

class RealtyRepository @Inject constructor(
    private val service: RealtyService,
    private val houseDao: HouseDao
) {

    suspend fun getSearchResidential(
        bedRange: String,
        bathRange: String,
        minPrice: String,
        maxPrice: String
    ) {
        try {
            val houses = service.listResidential(
                curPage = "1",
                bedRange = bedRange,
                bathRange = bathRange,
                minPrice = minPrice,
                maxPrice = maxPrice)
            .toHouses()?.let { houseDao.insertAll(it) }
        } catch (exception: Exception) {
            Log.d(RealtyRepository::class.simpleName, exception.localizedMessage)
        }
    }

    fun getHouses() = houseDao.getHouses()

    fun getHouse(houseId: String) = houseDao.getHouse(houseId)
}
