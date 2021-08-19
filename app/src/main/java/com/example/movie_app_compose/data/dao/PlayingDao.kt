package com.example.movie_app_compose.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movie_app_compose.data.entity.Playing

@Dao
interface PlayingDao {
    @Query(value = "SELECT * FROM playing")
    fun getAllPlayingMovies():List<Playing>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllMovies(list : List<Playing>)
}