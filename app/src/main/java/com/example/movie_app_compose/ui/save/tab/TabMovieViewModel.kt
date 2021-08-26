package com.example.movie_app_compose.ui.save.tab

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.movie_app_compose.data.Repository
import com.example.movie_app_compose.data.entity.MyMovie

class TabMovieViewModel : ViewModel() {
    lateinit var repository : Repository

    fun getAllMyMovies(): LiveData<List<MyMovie>>{
        repository.requestAllMyMovies()
        return repository.getAllMyMovies()
    }
}