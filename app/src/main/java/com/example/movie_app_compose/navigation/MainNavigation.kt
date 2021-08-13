package com.example.movie_app_compose.navigation

sealed class MainNavigation(val router : String) {
    object Detail : MainNavigation("detail")
    object SplashScreen : MainNavigation("splashscreen")
    object MainActivity : MainNavigation("mainactivity")
}
