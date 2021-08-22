package com.example.movie_app_compose.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.movie_app_compose.data.Repository
import com.example.movie_app_compose.model.Detail

class DetailViewModel : ViewModel() {
    lateinit var repository: Repository

    fun getDetail(id : String, type : String):LiveData<Detail>{
        repository.requestDetail(id, type)
        return repository.getDetail()
    }
}