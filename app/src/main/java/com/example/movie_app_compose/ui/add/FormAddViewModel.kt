package com.example.movie_app_compose.ui.add

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.movie_app_compose.BuildConfig.TAG
import com.example.movie_app_compose.data.Repository
import com.example.movie_app_compose.data.entity.*

class FormAddViewModel : ViewModel() {
    lateinit var repositor : Repository

    fun insertTrendingMovie(data : TrendingLocal){
        repositor.addTrendingMovies(data)
    }

    fun insertOnTheAir(data : OnTheAirLocal){
        repositor.addOnTheAirTvShow(data)
    }
}