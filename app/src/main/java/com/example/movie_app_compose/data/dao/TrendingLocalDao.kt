package com.example.movie_app_compose.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movie_app_compose.data.entity.Trending
import com.example.movie_app_compose.data.entity.TrendingLocal

@Dao
interface TrendingLocalDao {
    @Query(value = "SELECT * FROM TrendingLocal ORDER BY id_local ASC")
    fun getTrending():List<TrendingLocal>

    @Query(value = "SELECT * FROM TrendingLocal WHERE id_local=:id")
    fun getTrendingById(id : String):TrendingLocal

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: List<TrendingLocal>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data : TrendingLocal)

    @Query("DELETE FROM TrendingLocal WHERE id_local=:id")
    fun deleteById(id: String)
}