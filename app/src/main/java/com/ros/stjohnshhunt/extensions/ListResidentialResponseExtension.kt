package com.ros.stjohnshhunt.extensions

import android.content.Context
import com.ros.stjohnshhunt.data.House
import com.ros.stjohnshhunt.data.ListResidentialResponse
import java.time.Instant
import java.util.*

fun ListResidentialResponse.toHouses(context: Context): List<House>? {
    return Results?.sortedBy { it.InsertedDateUTC }?.map { result ->
        House(
            houseId = result.Id,
            desc = result.PublicRemarks,
            price = result.Property?.Price,
            address = result.Property?.Address?.AddressText?.replace("|", ", "),
            latLng = "${result.Property?.Address?.Latitude},${result.Property?.Address?.Longitude}",
            noOfBathrooms = result.Building?.BathroomTotal,
            noOfBedrooms = result.Building?.Bedrooms,
            noOfStories = result.Building?.StoriesTotal,
            interiorSize = result.Building?.SizeInterior,
            buildingType = result.Building?.Type,
            listingDateTimestamp = getListingTimestamp(result.TimeOnRealtor),
            houseImageUrl = result.Property?.Photo?.firstOrNull()?.HighResPath ?: "",
            houseDetailsUrl = result.RelativeDetailsURL
        )
    }
}

private fun getListingTimestamp(timeOnRealtor: String?): Long {
    val daysAndHours = calcDaysOnRealtor(timeOnRealtor)
    daysAndHours?.let {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, it.first * -1)
        calendar.add(Calendar.HOUR_OF_DAY, it.second * -1)
        return calendar.timeInMillis
    }
    return 0L
}

//"TimeOnRealtor": "6 days ago"
//"TimeOnRealtor": "6 hours ago"
private fun calcDaysOnRealtor(timeOnRealtor: String?): Pair<Int, Int>? {
    timeOnRealtor?.let {
        var days = 0
        var hours = 0
        if (it.contains("day")) {
            val dayString = it.substringBefore(" day")
            days = try {
                dayString.toInt()
            } catch(e: Exception) {
                0
            }
        } else if (it.contains("hour")) {
            val hourString = it.substringBefore(" hour")
            hours = try {
                hourString.toInt()
            } catch(e: Exception) {
                0
            }
        }
        return Pair(days, hours)
    }
    return null
}