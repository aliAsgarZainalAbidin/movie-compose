package com.example.movie_app_compose.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.Nullable
import androidx.annotation.StringRes
import com.example.movie_app_compose.R

sealed class Navigation(val router : String) {
    object Detail : Navigation("detail")
    object SplashScreen : Navigation("splashscreen")
    object Activity : Navigation("mainactivity")
}


