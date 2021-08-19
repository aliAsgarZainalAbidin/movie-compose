package com.example.movie_app_compose.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.movie_app_compose.data.dao.OnTheAirDao
import com.example.movie_app_compose.data.dao.PersonDao
import com.example.movie_app_compose.data.dao.PlayingDao
import com.example.movie_app_compose.data.dao.TrendingDao
import com.example.movie_app_compose.model.*
import com.example.movie_app_compose.data.entity.OnTheAir
import com.example.movie_app_compose.data.entity.People
import com.example.movie_app_compose.data.entity.Playing
import com.example.movie_app_compose.data.entity.Trending

@Database(
    entities = arrayOf(
        People::class,
        Trending::class,
        OnTheAir::class,
        Playing::class
    ), version = 1,
    exportSchema = false
)
@TypeConverters(value = arrayOf(Converters::class))
abstract class AppDatabase : RoomDatabase() {
    abstract fun PersonDao(): PersonDao
    abstract fun TrendingDao(): TrendingDao
    abstract fun OnTheAirDao(): OnTheAirDao
    abstract fun PlayingDao() : PlayingDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "movie-compose-database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
