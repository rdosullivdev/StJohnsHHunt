package com.ros.stjohnshhunt.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "houses")
data class House(
    @PrimaryKey @ColumnInfo(name = "id") val _id: String,
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
