package com.example.movie_app_compose.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movie_app_compose.data.entity.OnTheAirLocal
import com.example.movie_app_compose.data.entity.Trending
import com.example.movie_app_compose.data.entity.TrendingLocal

@Dao
interface OnTheAirLocalDao {
    @Query(value = "SELECT * FROM OnTheAirLocal ORDER BY id ASC")
    fun getOnTheAirLocal():List<OnTheAirLocal>

    @Query(value = "SELECT * FROM OnTheAirLocal WHERE id=:id")
    fun getOnTheAirLocalById(id : String):OnTheAirLocal

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<OnTheAirLocal>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data : OnTheAirLocal)
}