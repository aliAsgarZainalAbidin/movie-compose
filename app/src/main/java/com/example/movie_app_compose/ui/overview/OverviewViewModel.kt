package com.example.movie_app_compose.ui.overview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.movie_app_compose.BuildConfig.TAG
import com.example.movie_app_compose.data.Repository
import com.example.movie_app_compose.data.entity.OnTheAir
import com.example.movie_app_compose.data.entity.People
import com.example.movie_app_compose.data.entity.Trending

class OverviewViewModel : ViewModel() {
    lateinit var repositor : Repository

    fun getPopularPeople():LiveData<List<People>>{
        repositor.requestAllPeople()
        val data = repositor.getAllPeople()
        Log.d(TAG, "getPopularPeople: ${data.value}")
        return data
    }

    fun getTrendingMovies():LiveData<List<Trending>>{
        repositor.requestTrendingMovie()
        return repositor.getTrending()
    }

    fun getOnTheAir():LiveData<List<OnTheAir>>{
        repositor.requestOnTheAir()
        return repositor.getOnTheAir()
    }
}