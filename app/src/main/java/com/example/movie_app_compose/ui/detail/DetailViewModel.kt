package com.example.movie_app_compose.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.movie_app_compose.data.Repository
import com.example.movie_app_compose.data.entity.MyMovie
import com.example.movie_app_compose.data.entity.MyTvShow
import com.example.movie_app_compose.model.Detail
import com.example.movie_app_compose.model.TvShow

class DetailViewModel : ViewModel() {
    lateinit var repository: Repository

    fun getDetail(id : String, type : String):LiveData<Detail>{
        repository.requestDetail(id, type)
        return repository.getDetail()
    }

    fun getMovieById(id: String):LiveData<MyMovie>{
        repository.requestMovieById(id)
        return repository.getMovieById()
    }

    fun insertToMyMovies(myMovie: MyMovie){
        repository.insertToMyMovie(myMovie)
    }

    fun deleteMovieById(id: String){
        repository.deleteMovieById(id)
    }

    fun getTvShowById(id: String):LiveData<MyTvShow>{
        repository.requestTvShowById(id)
        return repository.getTvShowById()
    }

    fun insertToMyTvShow(tvShow: MyTvShow){
        repository.insertToTvShow(tvShow)
    }

    fun deleteTvShowById(id: String){
        repository.deleteTvShowById(id)
    }

}