package com.example.movie_app_compose.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.movie_app_compose.data.Repository
import com.example.movie_app_compose.data.entity.Playing

class MovieViewModel : ViewModel() {
    lateinit var repository: Repository

    fun getPlayingMovies():LiveData<List<Playing>>{
        repository.requestPlayingMovies()
        return repository.getPlayingMovies()
    }
}