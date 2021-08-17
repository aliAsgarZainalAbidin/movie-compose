package com.example.movie_app_compose.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.movie_app_compose.data.dao.PersonDao
import com.example.movie_app_compose.model.*

@Database(
    entities = arrayOf(
        People::class,
        Genre::class,
        KnownFor::class,
        Movie::class,
        TvShow::class
    ), version = 1,
    exportSchema = false
)
@TypeConverters(value = arrayOf(Converters::class))
abstract class AppDatabase : RoomDatabase() {
    abstract fun PersonDao(): PersonDao

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
