package com.example.movie_app_compose.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movie_app_compose.data.entity.AiringToday

@Dao
interface AiringTodayDao {
    @Query("SELECT * FROM AiringToday")
    fun getAllAiringToday(): List<AiringToday>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserAll(list: List<AiringToday>)
}