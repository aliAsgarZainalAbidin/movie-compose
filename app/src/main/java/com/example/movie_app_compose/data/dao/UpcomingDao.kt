package com.example.movie_app_compose.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movie_app_compose.data.entity.Upcoming

@Dao
interface UpcomingDao {
    @Query(value = "SELECT * FROM Upcoming")
    fun getUpcoming():List<Upcoming>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<Upcoming>)
}