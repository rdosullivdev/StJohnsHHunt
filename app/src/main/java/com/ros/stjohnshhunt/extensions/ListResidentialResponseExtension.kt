package com.ros.stjohnshhunt.extensions

import android.content.Context
import com.ros.stjohnshhunt.data.House
import com.ros.stjohnshhunt.data.ListResidentialResponse

fun ListResidentialResponse.toHouses(context: Context): List<House>? {
    return Results?.map { result ->
        House(
            houseId = result.Id,
            desc = result.PublicRemarks,
            price = result.Property?.Price,
            address = result.Property?.Address?.AddressText,
            latLng = "${result.Property?.Address?.Latitude},${result.Property?.Address?.Longitude}",
            noOfBathrooms = result.Building?.BathroomTotal,
            noOfBedrooms = result.Building?.Bedrooms,
            noOfStories = result.Building?.StoriesTotal,
            interiorSize = result.Building?.SizeInterior,
            buildingType = result.Building?.Type,
            listingDateUtc = result.InsertedDateUTC ?: 0,
            daysOnRealtor = calcDaysOnRealtor(result.TimeOnRealtor),
            houseImageUrl = result.Property?.Photo?.firstOrNull()?.HighResPath ?: "",
            houseDetailsUrl = result.RelativeDetailsURL
        )
    }
}

//"TimeOnRealtor": "6 days ago"
//"TimeOnRealtor": "6 hours ago"
private fun calcDaysOnRealtor(timeOnRealtor: String?): Int {
    timeOnRealtor?.let {
        if (it.contains("day")) {
            val days = it.substringBefore(" day")
            return try {
                days.toInt()
            } catch(e: Exception) {
                -1
            }
        }
    }
    return -1
}