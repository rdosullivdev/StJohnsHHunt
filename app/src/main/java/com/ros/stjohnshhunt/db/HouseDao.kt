package com.ros.stjohnshhunt.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ros.stjohnshhunt.data.House
import kotlinx.coroutines.flow.Flow

@Dao
interface HouseDao {

    @Query("SELECT * FROM houses ORDER BY listingDateUtc DESC")
    fun getHouses(): Flow<List<House>>

    @Query("SELECT * FROM houses WHERE id = :houseId")
    fun getHouse(houseId: String): Flow<House>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(houses: List<House>)

}