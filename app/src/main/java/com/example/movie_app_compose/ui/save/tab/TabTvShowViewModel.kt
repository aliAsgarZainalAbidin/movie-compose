package com.example.movie_app_compose.ui.save.tab

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.movie_app_compose.data.Repository
import com.example.movie_app_compose.data.entity.MyMovie
import com.example.movie_app_compose.data.entity.MyTvShow

class TabTvShowViewModel : ViewModel() {
    lateinit var repository : Repository

    fun getAllTvShow(): LiveData<List<MyTvShow>>{
        repository.requestAllMyTvShow()
        return repository.getAllMyTvShow()
    }
}