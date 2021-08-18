package com.example.movie_app_compose.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movie_app_compose.model.entity.OnTheAir

@Dao
interface OnTheAirDao {
    @Query(value = "SELECT * FROM ONTHEAIR")
    fun getAllOnTheAir():List<OnTheAir>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<OnTheAir>)
}