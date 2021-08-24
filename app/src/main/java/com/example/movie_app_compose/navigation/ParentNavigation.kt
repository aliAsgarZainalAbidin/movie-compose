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
            "${Navigation.Detail.router}/{type}/{idItem}",
            arguments = listOf(
                navArgument("idItem") {
                    type = NavType.StringType
                },
                navArgument("type") {
                    type = NavType.StringType
                }
            )
        ) {
            val id = it.arguments?.getString("idItem") ?: ""
            val type = it.arguments?.getString("type") ?: ""
            val restApi by lazy { ApiFactory.create() }
            val detailViewModel: DetailViewModel = viewModel()
            detailViewModel.repository = Repository(
                apiInterface = restApi, appDatabase = AppDatabase.getDatabase(
                    LocalContext.current
                )
            )
            val remoteData = detailViewModel.getDetail(id, type).observeAsState()
            val title: String
            val titleDate: String
            val date: String
            val listGenre = remoteData.value?.genres ?: listOf()
            val imageUrl = "${BuildConfig.BASE_IMAGE_URL}${remoteData.value?.backdrop_path}"
            val adult = if (remoteData.value?.adult == true) "YES" else "NO"
            val language = remoteData.value?.original_language.toString()
            val overview = remoteData.value?.overview.toString()
            val popularity = remoteData.value?.popularity?.toInt().toString()
            Log.d(TAG, "ParentNavigation: $listGenre")
            
            when (type) {
                Const.TYPE_MOVIE -> {
                    title = remoteData.value?.title.toString()
                    titleDate = "Release Date"
                    date = remoteData.value?.release_date.toString()
                    Detail(
                        id = id,
                        title = title,
                        imageUrl = imageUrl,
                        titleDate = titleDate,
                        date = date,
                        adult = adult,
                        overview = overview,
                        language = language,
                        popularity = popularity,
                        listGenre = listGenre
                    )
                }
                Const.TYPE_TV -> {
                    title = remoteData.value?.name.toString()
                    titleDate = "First Air Date"
                    date = remoteData.value?.first_air_date.toString()
                    Detail(
                        id = id,
                        title = title,
                        imageUrl = imageUrl,
                        titleDate = titleDate,
                        date = date,
                        adult = adult,
                        overview = overview,
                        language = language,
                        popularity = popularity,
                        listGenre = listGenre
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