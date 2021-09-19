package com.example.movie_app_compose.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.movie_app_compose.data.dao.*
import com.example.movie_app_compose.data.entity.*
import com.example.movie_app_compose.model.*

@Database(
    entities = arrayOf(
        People::class,
        Trending::class,
        OnTheAir::class,
        Playing::class,
        Upcoming::class,
        PopularMovies::class,
        AiringToday::class,
        PopularTv::class,
        MyMovie::class,
        MyTvShow::class,
        TrendingLocal::class,
    ), version = 1,
    exportSchema = false
)
@TypeConverters(value = arrayOf(Converters::class))
abstract class AppDatabase : RoomDatabase() {
    abstract fun PersonDao(): PersonDao
    abstract fun TrendingDao(): TrendingDao
    abstract fun OnTheAirDao(): OnTheAirDao
    abstract fun PlayingDao(): PlayingDao
    abstract fun UpcomingDao(): UpcomingDao
    abstract fun PopularMoviesDao(): PopularMoviesDao
    abstract fun AiringTodayDao(): AiringTodayDao
    abstract fun PopularTvDao(): PopularTvDao
    abstract fun MyMovieDao(): MyMovieDao
    abstract fun MyTvShowDao(): MyTvShowDao
    abstract fun TrendingLocalDao() : TrendingLocalDao

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
