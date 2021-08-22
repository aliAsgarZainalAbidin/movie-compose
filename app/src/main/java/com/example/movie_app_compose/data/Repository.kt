package com.example.movie_app_compose.data

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.movie_app_compose.BuildConfig
import com.example.movie_app_compose.BuildConfig.API
import com.example.movie_app_compose.BuildConfig.TAG
import com.example.movie_app_compose.api.ApiInterface
import com.example.movie_app_compose.data.entity.*
import com.example.movie_app_compose.model.Detail
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
    private lateinit var mLiveDataTrendingMovie: MutableLiveData<List<Trending>>
    private lateinit var mOnTheAir: MutableLiveData<List<OnTheAir>>
    private lateinit var mPlaying: MutableLiveData<List<Playing>>
    private lateinit var mUpcoming: MutableLiveData<List<Upcoming>>
    private lateinit var mPopularMovies: MutableLiveData<List<PopularMovies>>
    private lateinit var mAiringToday: MutableLiveData<List<AiringToday>>
    private lateinit var mPopularTv: MutableLiveData<List<PopularTv>>
    private lateinit var mDetail: MutableLiveData<Detail>
    private lateinit var people: ArrayList<People>

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
        people = arrayListOf()
        val remoteListPerson = ArrayList<People>()
        val result = apiInterface.getPopularPerson(API)
        Log.d(TAG, "getAllPerson: ${result}")
        result.enqueue(object : Callback<Root<People>> {
            override fun onResponse(call: Call<Root<People>>, response: Response<Root<People>>) {
                if (response.isSuccessful) {
                    val dataSource = response.body()
                    for (data in dataSource?.results!!) {
                        remoteListPerson.add(data)
                    }

                    CoroutineScope(Dispatchers.IO).launch {
                        people.addAll(appDatabase.PersonDao().getAllPerson())
                        if (people == remoteListPerson) {
                            // Autosort database membuat block code ini tidak pernah running
                            Log.d(TAG, "onResponse: OPSI 1")
                            mutableLiveDataPeople.postValue(people)
                        } else {
                            Log.d(TAG, "onResponse: OPSI 2")
                            appDatabase.PersonDao().insertAll(remoteListPerson)
                            mutableLiveDataPeople.postValue(remoteListPerson)
                        }
                    }

                } else {
                    Log.d(TAG, "onResponse: fail${response.body()}")
                }
            }

            override fun onFailure(call: Call<Root<People>>, t: Throwable) {
                Log.d(TAG, "onFailure: $t")
                CoroutineScope(Dispatchers.IO).launch {
                    people.addAll(appDatabase.PersonDao().getAllPerson())
                    mutableLiveDataPeople.postValue(people)
                }
            }

        })
    }

    fun getAllPeople(): LiveData<List<People>> {
        return mutableLiveDataPeople
    }

    fun requestTrendingMovie() {
        mLiveDataTrendingMovie = MutableLiveData()
        var movies = ArrayList<Trending>()
        var remoteMovies = ArrayList<Trending>()
        val result = apiInterface.getTrendingMovies(API)
        result.enqueue(object : Callback<Root<Trending>> {
            override fun onResponse(
                call: Call<Root<Trending>>,
                response: Response<Root<Trending>>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    data?.results?.let { remoteMovies.addAll(it) }
                    CoroutineScope(Dispatchers.IO).launch {
                        appDatabase.TrendingDao().insertAll(remoteMovies)
                        mLiveDataTrendingMovie.postValue(remoteMovies)
                    }
                } else {
                    Log.d(TAG, "onResponse: Failed $response")
                }
            }

            override fun onFailure(call: Call<Root<Trending>>, t: Throwable) {
                Log.d(TAG, "onFailure: $t")
                CoroutineScope(Dispatchers.IO).launch {
                    movies.addAll(appDatabase.TrendingDao().getTrending())
                    mLiveDataTrendingMovie.postValue(movies)
                }
            }

        })
    }

    fun getTrending(): LiveData<List<Trending>> {
        return mLiveDataTrendingMovie
    }

    fun requestOnTheAir() {
        mOnTheAir = MutableLiveData()
        var tvShow = ArrayList<OnTheAir>()
        var remoteTvShow = ArrayList<OnTheAir>()
        val result = apiInterface.getOnTheAir(API)
        result.enqueue(object : Callback<Root<OnTheAir>> {
            override fun onResponse(
                call: Call<Root<OnTheAir>>,
                response: Response<Root<OnTheAir>>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    data?.results?.let { remoteTvShow.addAll(it) }
                    CoroutineScope(Dispatchers.IO).launch {
                        appDatabase.OnTheAirDao().insertAll(remoteTvShow)
                        mOnTheAir.postValue(remoteTvShow)
                    }
                } else {
                    Log.d(TAG, "onResponse: $response")
                }
            }

            override fun onFailure(call: Call<Root<OnTheAir>>, t: Throwable) {
                Log.d(TAG, "onFailure: $t")
                CoroutineScope(Dispatchers.IO).launch {
                    tvShow.addAll(appDatabase.OnTheAirDao().getAllOnTheAir())
                    mOnTheAir.postValue(tvShow)
                }
            }

        })
    }

    fun getOnTheAir(): LiveData<List<OnTheAir>> {
        return mOnTheAir
    }

    fun requestPlayingMovies() {
        mPlaying = MutableLiveData()
        var playingMovies = ArrayList<Playing>()
        var remotePlayingMovies = ArrayList<Playing>()
        val result = apiInterface.getNowPlaying(API)
        result.enqueue(object : Callback<Root<Playing>> {
            override fun onResponse(call: Call<Root<Playing>>, response: Response<Root<Playing>>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    data?.results?.let { remotePlayingMovies.addAll(it) }
                    CoroutineScope(Dispatchers.IO).launch {
                        appDatabase.PlayingDao().insertAllMovies(remotePlayingMovies)
                        mPlaying.postValue(remotePlayingMovies)
                    }
                } else {
                    Log.d(TAG, "onResponse: $response")
                }
            }

            override fun onFailure(call: Call<Root<Playing>>, t: Throwable) {
                Log.d(TAG, "onFailure: $t")
                CoroutineScope(Dispatchers.IO).launch {
                    playingMovies.addAll(appDatabase.PlayingDao().getAllPlayingMovies())
                    mPlaying.postValue(playingMovies)
                }
            }
        })
    }

    fun getPlayingMovies(): LiveData<List<Playing>> {
        return mPlaying
    }

    fun requestUpcoming() {
        mUpcoming = MutableLiveData()
        val upcoming = ArrayList<Upcoming>()
        val remoteUpcoming = ArrayList<Upcoming>()
        val result = apiInterface.getUpcoming(API)
        result.enqueue(object : Callback<Root<Upcoming>> {
            override fun onResponse(
                call: Call<Root<Upcoming>>,
                response: Response<Root<Upcoming>>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    data?.results?.let { remoteUpcoming.addAll(it) }
                    CoroutineScope(Dispatchers.IO).launch {
                        appDatabase.UpcomingDao().insertAll(remoteUpcoming)
                        mUpcoming.postValue(remoteUpcoming)
                    }
                } else {
                    Log.d(TAG, "onResponse: $response")
                }
            }

            override fun onFailure(call: Call<Root<Upcoming>>, t: Throwable) {
                Log.d(TAG, "onFailure: $t")
                CoroutineScope(Dispatchers.IO).launch {
                    upcoming.addAll(appDatabase.UpcomingDao().getUpcoming())
                    mUpcoming.postValue(upcoming)
                }
            }

        })
    }

    fun getUpcomingMovies(): LiveData<List<Upcoming>> {
        return mUpcoming
    }

    fun requestPopularMovies() {
        mPopularMovies = MutableLiveData()
        val popularMovies = ArrayList<PopularMovies>()
        val remotePopularMovies = ArrayList<PopularMovies>()
        val result = apiInterface.getPopularMovies(API)
        result.enqueue(object : Callback<Root<PopularMovies>> {
            override fun onResponse(
                call: Call<Root<PopularMovies>>,
                response: Response<Root<PopularMovies>>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    data?.results?.let { remotePopularMovies.addAll(it) }
                    CoroutineScope(Dispatchers.IO).launch {
                        appDatabase.PopularMoviesDao().insertAll(remotePopularMovies)
                        mPopularMovies.postValue(remotePopularMovies)
                    }
                } else {
                    Log.d(TAG, "onResponse: $response")
                }
            }

            override fun onFailure(call: Call<Root<PopularMovies>>, t: Throwable) {
                Log.d(TAG, "onFailure: $t")
                CoroutineScope(Dispatchers.IO).launch {
                    popularMovies.addAll(appDatabase.PopularMoviesDao().getPopularMovies())
                    mPopularMovies.postValue(popularMovies)
                }
            }

        })
    }

    fun getPopularMovies(): LiveData<List<PopularMovies>> {
        return mPopularMovies
    }

    fun requestAiringToday() {
        mAiringToday = MutableLiveData()
        val airingToday = ArrayList<AiringToday>()
        val remoteAiringToday = ArrayList<AiringToday>()
        val result = apiInterface.getAiringToday(API)
        result.enqueue(object : Callback<Root<AiringToday>> {
            override fun onResponse(
                call: Call<Root<AiringToday>>,
                response: Response<Root<AiringToday>>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    data?.results?.let { remoteAiringToday.addAll(it) }
                    CoroutineScope(Dispatchers.IO).launch {
                        appDatabase.AiringTodayDao().inserAll(remoteAiringToday)
                        mAiringToday.postValue(remoteAiringToday)
                    }
                } else {
                    Log.d(TAG, "onResponse: $response")
                }
            }

            override fun onFailure(call: Call<Root<AiringToday>>, t: Throwable) {
                Log.d(TAG, "onFailure: $t")
                CoroutineScope(Dispatchers.IO).launch {
                    airingToday.addAll(appDatabase.AiringTodayDao().getAllAiringToday())
                    mAiringToday.postValue(airingToday)
                }
            }

        })
    }

    fun getAiringToday(): LiveData<List<AiringToday>> {
        return mAiringToday
    }

    fun requestPopularTvShow() {
        mPopularTv = MutableLiveData()
        val popularTv = ArrayList<PopularTv>()
        val remotePopularTv = ArrayList<PopularTv>()
        val result = apiInterface.getPopularTvShow(API)
        result.enqueue(object : Callback<Root<PopularTv>> {
            override fun onResponse(
                call: Call<Root<PopularTv>>,
                response: Response<Root<PopularTv>>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    data?.results?.let { remotePopularTv.addAll(it) }
                    CoroutineScope(Dispatchers.IO).launch {
                        appDatabase.PopularTvDao().inserAll(remotePopularTv)
                        mPopularTv.postValue(remotePopularTv)
                    }
                } else {
                    Log.d(TAG, "onResponse: $response")
                }
            }

            override fun onFailure(call: Call<Root<PopularTv>>, t: Throwable) {
                Log.d(TAG, "onFailure: $t")
                CoroutineScope(Dispatchers.IO).launch {
                    popularTv.addAll(appDatabase.PopularTvDao().getAllPopularTv())
                    mPopularTv.postValue(popularTv)
                }
            }

        })
    }

    fun getPopularTvShow(): LiveData<List<PopularTv>> {
        return mPopularTv
    }

    fun requestDetail(id: String, type: String) {
        mDetail = MutableLiveData()
        var remoteDetail: Detail
        val result = apiInterface.getDetail(type, id, API)
        result.enqueue(object : Callback<Detail> {
            override fun onResponse(call: Call<Detail>, response: Response<Detail>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        remoteDetail = Detail(
                            data.adult,
                            data.backdrop_path,
                            data.budget,
                            data.homepage,
                            data.first_air_date,
                            data.name,
                            data.id,
                            data.imdb_id,
                            data.original_language,
                            data.original_title,
                            data.overview,
                            data.popularity,
                            data.poster_path,
                            data.production_companies,
                            data.production_countries,
                            data.release_date,
                            data.revenue,
                            data.runtime,
                            data.spoken_languages,
                            data.status,
                            data.tagline,
                            data.title,
                            data.video,
                            data.vote_average,
                            data.vote_count
                        )
                        mDetail.postValue(remoteDetail)
                    }
                } else {
                    Log.d(TAG, "onResponse: $response")
                }
            }

            override fun onFailure(call: Call<Detail>, t: Throwable) {
                Log.d(TAG, "onFailure: $t")
            }

        })
    }

    fun getDetail(): LiveData<Detail> {
        return mDetail
    }
}