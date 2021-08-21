package com.example.movie_app_compose.ui.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.movie_app_compose.data.Repository
import com.example.movie_app_compose.data.entity.AiringToday
import com.example.movie_app_compose.data.entity.OnTheAir
import com.example.movie_app_compose.data.entity.PopularTv

class TvViewModel : ViewModel() {
    lateinit var repository: Repository

    fun getOnTheAir(): LiveData<List<OnTheAir>> {
        repository.requestOnTheAir()
        return repository.getOnTheAir()
    }

    fun getAiringToday(): LiveData<List<AiringToday>>{
        repository.requestAiringToday()
        return repository.getAiringToday()
    }

    fun getPopularTv(): LiveData<List<PopularTv>>{
        repository.requestPopularTvShow()
        return repository.getPopularTvShow()
    }
}