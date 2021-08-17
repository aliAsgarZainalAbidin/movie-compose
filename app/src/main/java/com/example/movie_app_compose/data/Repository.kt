package com.example.movie_app_compose.data

import android.app.Person
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.movie_app_compose.BuildConfig
import com.example.movie_app_compose.BuildConfig.API
import com.example.movie_app_compose.BuildConfig.TAG
import com.example.movie_app_compose.api.ApiInterface
import com.example.movie_app_compose.model.People
import com.example.movie_app_compose.model.RequestWrapper
import com.example.movie_app_compose.model.Root
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository(val apiInterface: ApiInterface, val appDatabase: AppDatabase) {
    private var token: String = ""
    private var sessionId: String = ""
    private var isLogin: Boolean = false
    private lateinit var mutableLiveDataPeople: MutableLiveData<List<People>>

    fun createRequestToken(context: Context): String {
        val result = apiInterface.createRequestToken(BuildConfig.API)
        result.enqueue(object : Callback<RequestWrapper> {
            override fun onResponse(
                call: Call<RequestWrapper>,
                response: Response<RequestWrapper>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    token = data?.requestToken.toString()
                    Log.d(TAG, "onResponse: CreateRequestToken is Successful $token")

                    var intent = Intent(Intent.ACTION_VIEW)
                    val url =
                        "${BuildConfig.WEB_BASE_URL}$token?redirect_to=app://${BuildConfig.APPLICATION_ID}/approved"
                    intent.setData(Uri.parse(url))
                    context.startActivity(intent)
                    Log.d(TAG, "onResponse: URL $url")
                    createSessionId(token)
                } else {
                    Log.d(
                        TAG,
                        "onResponse: CreateRequestToken is Not Successful ${response.body()}"
                    )
                }
            }

            override fun onFailure(call: Call<RequestWrapper>, t: Throwable) {
                Log.d(TAG, "onFailure: CreateRequestToken$t")
            }

        })
        return token
    }

    fun createSessionId(token: String): RequestWrapper {
        val result = apiInterface.createSessionId(BuildConfig.API, requestToken = token)
        val requestWrapper = RequestWrapper()
        result.enqueue(object : Callback<RequestWrapper> {
            override fun onResponse(
                call: Call<RequestWrapper>,
                response: Response<RequestWrapper>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    sessionId = data?.sessionId.toString()
                    isLogin = data?.success ?: false

                    requestWrapper.sessionId = sessionId
                    requestWrapper.success = isLogin
                    Log.d(TAG, "onResponse: CreateSessionId is Successful ${response.body()}")
                } else {
                    Log.d(TAG, "onResponse: CreateSessionId is Not Successful ${response.body()}")
                }
            }

            override fun onFailure(call: Call<RequestWrapper>, t: Throwable) {
                Log.d(TAG, "onFailure: CreateSessionId $t")
            }

        })
        return requestWrapper
    }

    fun requestAllPeople() {
        mutableLiveDataPeople = MutableLiveData()
        var listPerson = ArrayList<People>()
//        CoroutineScope(Dispatchers.IO).launch {
//            listPerson = appDatabase.PersonDao().getAllPerson() as ArrayList<People>
//        }
        val remoteListPerson = ArrayList<People>()
        val result = apiInterface.getPopularPerson(API)
        Log.d(TAG, "getAllPerson: ${result}")
        result.enqueue(object : Callback<Root<People>> {
            override fun onResponse(call: Call<Root<People>>, response: Response<Root<People>>) {
                if (response.isSuccessful) {
                    val dataSource = response.body()
                    for (data in dataSource?.results!!) {
                        remoteListPerson.add(data)
                        Log.d(TAG, "onResponse: data $data")
                    }
                    mutableLiveDataPeople.postValue(remoteListPerson)
                    Log.d(TAG, "onResponse: Succe${remoteListPerson.size}")
                } else {
                    Log.d(TAG, "onResponse: fail${response.body()}")
                }
            }

            override fun onFailure(call: Call<Root<People>>, t: Throwable) {
                Log.d(TAG, "onFailure: err ${t}")
            }

        })
//        return if (listPerson.equals(remoteListPerson)) {
//            Log.d(TAG, "getAllPerson: 1=>${MutableLiveData(listPerson).value}")
//            MutableLiveData(listPerson)
//        } else {
//            appDatabase.PersonDao().insertAll(remoteListPerson)
//            CoroutineScope(Dispatchers.IO).launch {
//                listPerson = appDatabase.PersonDao().getAllPerson() as ArrayList<People>
//                Log.d(TAG, "getAllPerson: 2=>${MutableLiveData(listPerson).value}")
//            }
//            MutableLiveData(listPerson)
//        }
    }

    fun getAllPeople(): LiveData<List<People>> {
        return mutableLiveDataPeople
    }

}