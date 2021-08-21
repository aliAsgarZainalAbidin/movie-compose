package com.example.movie_app_compose.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movie_app_compose.data.entity.AiringToday
import com.example.movie_app_compose.data.entity.PopularTv

@Dao
interface PopularTvDao {
    @Query("SELECT * FROM PopularTv")
    fun getAllPopularTv(): List<PopularTv>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserAll(list: List<PopularTv>)
}