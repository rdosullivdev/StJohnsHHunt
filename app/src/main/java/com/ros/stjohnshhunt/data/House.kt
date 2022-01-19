package com.ros.stjohnshhunt.data

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat


@Entity(tableName = "houses")
data class House(
    @PrimaryKey @ColumnInfo(name = "id") val houseId: String,
    val desc: String?,
    val price: String?,
    val address: String?,
    val latLng: String?,
    val noOfBathrooms: String?,
    val noOfBedrooms: String?,
    val noOfStories: String?,
    val interiorSize: String?,
    val buildingType: String?,
    val listingDateUtc: Long,
    val daysOnRealtor: Int = 0,
    val houseImageUrl: String?,
    val houseDetailsUrl: String?
) {

    fun getGMapsUri(): Uri = Uri.parse("geo:$latLng?q=${Uri.encode(address?.replace('|', ','))}")

    fun getDetailsUrl(): Uri = Uri.parse("http://www.realtor.ca${houseDetailsUrl?.replace("\\/", "/")}")
}
