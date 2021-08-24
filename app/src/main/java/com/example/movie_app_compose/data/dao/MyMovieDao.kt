package com.example.movie_app_compose.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movie_app_compose.data.entity.MyMovie
import com.example.movie_app_compose.data.entity.MyTvShow
import com.example.movie_app_compose.data.entity.Upcoming

@Dao
interface MyMovieDao {
    @Query(value = "SELECT * FROM MyMovie")
    fun getAllMovie():List<MyMovie>

    @Query("SELECT * FROM MyMovie WHERE id=:id")
    fun getMovieById(id : String): MyMovie

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<MyMovie>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data : MyMovie)
}