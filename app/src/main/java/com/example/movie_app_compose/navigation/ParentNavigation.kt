package com.example.movie_app_compose.navigation

import android.os.Handler
import android.util.Log
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.example.movie_app_compose.BuildConfig
import com.example.movie_app_compose.BuildConfig.TAG
import com.example.movie_app_compose.MainActivityContent
import com.example.movie_app_compose.api.ApiFactory
import com.example.movie_app_compose.data.AppDatabase
import com.example.movie_app_compose.data.Repository
import com.example.movie_app_compose.model.Genre
import com.example.movie_app_compose.ui.detail.Detail
import com.example.movie_app_compose.ui.detail.DetailViewModel
import com.example.movie_app_compose.ui.login.LoginContent
import com.example.movie_app_compose.ui.offline.OfflineContent
import com.example.movie_app_compose.ui.splash.SplashScreenContent
import com.example.movie_app_compose.util.Const

@Composable
fun ParentNavigation() {
    val navControllerMainUI = rememberNavController()
    var splashScreenActive by remember { mutableStateOf(false) }
    NavHost(
        navController = navControllerMainUI,
        startDestination = Navigation.SplashScreen.router
    ) {
        composable(Navigation.SplashScreen.router) {
            SplashScreenContent()
            Handler().postDelayed({
                navControllerMainUI.navigate(Navigation.Activity.router) {
//                        splashScreenActive = true
                    popUpTo(Navigation.SplashScreen.router) {
                        inclusive = true
                    }
                    launchSingleTop = true
                    restoreState = false
                }
            }, 1500)
        }
        composable(
            "${Navigation.Detail.router}/{type}/{idItem}/{repo}",
            arguments = listOf(
                navArgument("idItem") {
                    type = NavType.StringType
                },
                navArgument("type") {
                    type = NavType.StringType
                },
                navArgument("repo") {
                    type = NavType.StringType
                }
            )
        ) {
            val id = it.arguments?.getString("idItem") ?: ""
            val type = it.arguments?.getString("type") ?: ""
            val repo = it.arguments?.getString("repo") ?: ""
            val restApi by lazy { ApiFactory.create() }
            val detailViewModel: DetailViewModel = viewModel()
            detailViewModel.repository = Repository(
                apiInterface = restApi, appDatabase = AppDatabase.getDatabase(
                    LocalContext.current
                )
            )
            val remoteData = detailViewModel.getDetail(id, type).observeAsState()
            val localDataMovie = detailViewModel.getMovieById(id).observeAsState()
            val localDataTvShow = detailViewModel.getTvShowById(id).observeAsState()
            val localTrending = detailViewModel.getLocalTrending(id).observeAsState()
            Log.d(TAG, "ParentNavigation: $repo")
            var title = ""
            var titleDate= ""
            var date= ""
            var listGenre: List<Genre> = listOf()
            var imageUrl = ""
            var posterPath = ""
            var adult = ""
            var language = ""
            var overview = ""
            var popularity = ""
            var typeRepo : String? = null

            if (repo.equals(Const.TYPE_REPO_REMOTE)) {
                when(type){
                    Const.TYPE_MOVIE -> {
                        title = remoteData.value?.title.toString()
                        titleDate = "Release Date"
                        date = remoteData.value?.release_date.toString()
                        listGenre = remoteData.value?.genres ?: listOf()
                        imageUrl = "${BuildConfig.BASE_IMAGE_URL}${remoteData.value?.backdrop_path}"
                        posterPath = "${BuildConfig.BASE_IMAGE_URL}${remoteData.value?.poster_path}"
                        adult = if (remoteData.value?.adult == true) "YES" else "NO"
                        language = remoteData.value?.original_language.toString()
                        overview = remoteData.value?.overview.toString()
                        popularity = remoteData.value?.popularity?.toInt().toString()
                    }
                    Const.TYPE_TV -> {
                        title = remoteData.value?.name.toString()
                        titleDate = "First Air Date"
                        date = remoteData.value?.first_air_date.toString()
                        listGenre = remoteData.value?.genres ?: listOf()
                        imageUrl = "${BuildConfig.BASE_IMAGE_URL}${remoteData.value?.backdrop_path}"
                        posterPath = "${BuildConfig.BASE_IMAGE_URL}${remoteData.value?.poster_path}"
                        adult = "-"
                        language = remoteData.value?.original_language.toString()
                        overview = remoteData.value?.overview.toString()
                        popularity = remoteData.value?.popularity?.toInt().toString()
                    }
                }
            } else if (repo.equals(Const.TYPE_TRENDING_LOCAL)){
                when (type){
                    Const.TYPE_MOVIE -> {
                        title = localTrending.value?.title.toString()
                        titleDate = "Release Date"
                        date = localTrending.value?.releaseDate.toString()
                        listGenre = localTrending.value?.genres ?: listOf()
                        imageUrl = "${localTrending.value?.backdropPath}"
                        posterPath = "${localTrending.value?.posterPath}"
                        adult = if (localTrending.value?.adult == true) "YES" else "NO"
                        language = localTrending.value?.originalLanguage.toString()
                        overview = localTrending.value?.overview.toString()
                        popularity = localTrending.value?.popularity?.toInt().toString()
                        typeRepo = localTrending.value?.typeTrending.toString()
                    }
                    Const.TYPE_TV -> {
                       
                    }
                }
                Log.d(TAG, "ParentNavigation: TYPE_LOCAL_TREND")
            } else {
                when (type){
                    Const.TYPE_MOVIE -> {
                        title = localDataMovie.value?.title.toString()
                        titleDate = "Release Date"
                        date = localDataMovie.value?.releaseDate.toString()
                        listGenre = localDataMovie.value?.genreIds ?: listOf()
                        imageUrl = "${BuildConfig.BASE_IMAGE_URL}${localDataMovie.value?.backdropPath}"
                        posterPath = "${BuildConfig.BASE_IMAGE_URL}${localDataMovie.value?.posterPath}"
                        adult = if (localDataMovie.value?.adult == true) "YES" else "NO"
                        language = localDataMovie.value?.language.toString()
                        overview = localDataMovie.value?.overview.toString()
                        popularity = localDataMovie.value?.popularity?.toInt().toString()
                    }
                    Const.TYPE_TV -> {
                        title = localDataTvShow.value?.name.toString()
                        titleDate = "First Air Date"
                        date = localDataTvShow.value?.firstAirDate.toString()
                        listGenre = localDataTvShow.value?.genres ?: listOf()
                        imageUrl = "${BuildConfig.BASE_IMAGE_URL}${localDataTvShow.value?.backdropPath}"
                        posterPath = "${BuildConfig.BASE_IMAGE_URL}${localDataTvShow.value?.posterPath}"
                        adult = "-"
                        language = localDataTvShow.value?.language.toString()
                        overview = localDataTvShow.value?.overview.toString()
                        popularity = localDataTvShow.value?.popularity?.toInt().toString()
                    }
                }
            }

            when (type) {
                Const.TYPE_MOVIE -> {
                    Detail(
                        posterPath = posterPath,
                        id = id,
                        type = Const.TYPE_MOVIE,
                        title = title,
                        imageUrl = imageUrl,
                        titleDate = titleDate,
                        date = date,
                        adult = adult,
                        overview = overview,
                        language = language,
                        popularity = popularity,
                        listGenre = listGenre,
                        isSaved = localDataMovie.value?.isSaved ?: false,
                        navController = navControllerMainUI,
                        typeRepo = typeRepo ?: Const.TYPE_REPO_REMOTE
                    )
                }
                Const.TYPE_TV -> {
                    Detail(
                        posterPath = posterPath,
                        type = Const.TYPE_TV,
                        id = id,
                        title = title,
                        imageUrl = imageUrl,
                        titleDate = titleDate,
                        date = date,
                        adult = " - ",
                        overview = overview,
                        language = language,
                        popularity = popularity,
                        listGenre = listGenre,
                        isSaved = localDataTvShow.value?.isSaved ?: false,
                        navController = navControllerMainUI
                    )
                }
            }
        }
        composable(Navigation.Offline.router) {
            OfflineContent()
        }
        composable(Navigation.Activity.router) {
            MainActivityContent(navControllerMainUI)
        }
        composable(
            Navigation.Login.router
        ) {
            LoginContent(onClickButtonLogin = {
                navControllerMainUI.popBackStack(Navigation.SplashScreen.router, true, false)
                navControllerMainUI.navigate(Navigation.Activity.router)
            })
        }
    }
}