package com.ros.stjohnshhunt.data


//@Entity(tableName = "houses")
data class House(
    val _id: Long,
    val desc: String?,
    val price: String?,
    val address: String?,
    val noOfBathrooms: String?,
    val noOfBedrooms: String?,
    val noOfStories: String?,
    val buildingType: String?,
    val listingDateUtc: Long,
    val timeOnRealtor: String?,
    val houseImageUrl: String?
)

/*

 */