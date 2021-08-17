package com.example.movie_app_compose.ui.overview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.movie_app_compose.BuildConfig.TAG
import com.example.movie_app_compose.data.Repository
import com.example.movie_app_compose.model.People

class OverviewViewModel : ViewModel() {
    lateinit var repositor : Repository

    fun getPopularPeople():LiveData<List<People>>{
        repositor.requestAllPeople()
        val data = repositor.getAllPeople()
        Log.d(TAG, "getPopularPeople: ${data.value}")
        return data
    }
}