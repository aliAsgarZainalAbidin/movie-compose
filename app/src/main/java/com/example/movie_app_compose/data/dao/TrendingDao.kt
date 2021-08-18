package com.example.movie_app_compose.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movie_app_compose.model.Movie
import com.example.movie_app_compose.model.entity.Trending

@Dao
interface TrendingDao {
    @Query(value = "SELECT * FROM Trending")
    fun getTrending():List<Trending>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<Trending>)
}