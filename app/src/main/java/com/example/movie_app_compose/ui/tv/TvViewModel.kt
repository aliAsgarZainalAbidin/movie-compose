package com.example.movie_app_compose.ui.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.movie_app_compose.data.Repository
import com.example.movie_app_compose.data.entity.OnTheAir

class TvViewModel : ViewModel() {
    lateinit var repository: Repository

    fun getOnTheAir(): LiveData<List<OnTheAir>> {
        repository.requestOnTheAir()
        return repository.getOnTheAir()
    }
}