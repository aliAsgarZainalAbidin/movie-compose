package com.example.movie_app_compose.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movie_app_compose.model.entity.People

@Dao
interface PersonDao {
    @Query(value = "SELECT * FROM people")
    fun getAllPerson():List<People>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<People>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPeople(people: People)
}