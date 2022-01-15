package com.ros.stjohnshhunt.extensions

import com.ros.stjohnshhunt.data.House
import com.ros.stjohnshhunt.data.ListResidentialResponse

fun ListResidentialResponse.toHouses(): List<House>? {
    return Results?.map { result ->
        House(
            _id = result.Id.toLong(),
            desc = result.PublicRemarks,
            price = result.Property?.Price,
            address = result.Property?.Address?.AddressText,
            noOfBathrooms = result.Building?.BathroomTotal,
            noOfBedrooms = result.Building?.Bedrooms, //?.toInt() ?: 0,
            noOfStories = result.Building?.StoriesTotal, //?.toInt() ?: 0,
            buildingType = result.Building?.Type,
            listingDateUtc = result.InsertedDateUTC ?: 0,
            timeOnRealtor = result.TimeOnRealtor,
            houseImageUrl = result.Property?.Photo?.firstOrNull()?.HighResPath ?: ""
        )
    }
}
