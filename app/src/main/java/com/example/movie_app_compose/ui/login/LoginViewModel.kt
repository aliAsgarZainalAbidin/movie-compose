package com.example.movie_app_compose.ui.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movie_app_compose.BuildConfig.TAG
import com.example.movie_app_compose.data.Repository
import com.example.movie_app_compose.model.RequestWrapper

class LoginViewModel : ViewModel() {

    lateinit var repository: Repository
    private val _token = MutableLiveData("")
    private val _requestWrapper = MutableLiveData(RequestWrapper())
    var token: LiveData<String> = _token
    var requestWrapper: LiveData<RequestWrapper> = _requestWrapper


    fun createRequestToken(context: Context) {
        _token.value = repository.createRequestToken(context)
        Log.d(TAG, "createRequestToken: ${_token.value}")
    }

    fun createSessionId(token: String) : LiveData<RequestWrapper> {
        _requestWrapper.value = repository.createSessionId(token = token)
        return _requestWrapper
    }
}