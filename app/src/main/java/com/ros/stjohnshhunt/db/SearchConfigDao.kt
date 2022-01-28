package com.ros.stjohnshhunt.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ros.stjohnshhunt.data.House
import com.ros.stjohnshhunt.data.SearchConfig
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchConfigDao {

    @Query("SELECT * FROM search ORDER BY createdAtMillis DESC")
    fun getSearches(): Flow<List<SearchConfig>>

    @Query("SELECT * FROM search WHERE id = :searchId")
    fun getSearch(searchId: String): Flow<SearchConfig>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(search: SearchConfig)
}