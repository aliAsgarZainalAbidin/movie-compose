package com.example.movie_app_compose.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.movie_app_compose.data.Repository
import com.example.movie_app_compose.data.entity.Playing
import com.example.movie_app_compose.data.entity.Upcoming

class MovieViewModel : ViewModel() {
    lateinit var repository: Repository

    fun getPlayingMovies():LiveData<List<Playing>>{
        repository.requestPlayingMovies()
        return repository.getPlayingMovies()
    }

    fun getUpcomingMovies(): LiveData<List<Upcoming>>{
        repository.requestUpcoming()
        return repository.getUpcomingMovies()
    }
}