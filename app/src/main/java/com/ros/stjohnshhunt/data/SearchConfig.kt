package com.ros.stjohnshhunt.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search")
data class SearchConfig(
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    val createdAtMillis: Long,
    val name: String,
    val bedRange: String,
    val bathRange: String,
    val priceMin: String,
    val priceMax: String
) {

    fun searchSummary(): String {
        return "$name\nBeds: $bedRange/Baths: $bathRange\nPriceRange: $$priceMin-$$priceMax"
    }
}