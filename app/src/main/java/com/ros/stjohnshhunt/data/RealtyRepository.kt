package com.ros.stjohnshhunt.data

import android.util.Log
import com.ros.stjohnshhunt.api.RealtyService
import com.ros.stjohnshhunt.extensions.toHouses
import javax.inject.Inject

class RealtyRepository @Inject constructor(private val service: RealtyService) {

    suspend fun getSearchResidential(
        bedRange: String,
        bathRange: String,
        minPrice: String,
        maxPrice: String
    ): List<House>? {
        return try {
            service.listResidential(
                curPage = "1",
                bedRange = bedRange,
                bathRange = bathRange,
                minPrice = minPrice,
                maxPrice = maxPrice
            ).toHouses()
        } catch (exception: Exception) {
            Log.d(RealtyRepository::class.simpleName, exception.localizedMessage)
            null
        }
    }
}
