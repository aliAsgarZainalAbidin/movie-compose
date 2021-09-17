package com.example.movie_app_compose.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.Nullable
import androidx.annotation.StringRes
import com.example.movie_app_compose.R

sealed class Screen(
    val route: String,
    @StringRes val resId: Int,
    @DrawableRes @Nullable val iconId: Int
) {
    object Overview : Screen("overview", R.string.overview, R.drawable.ic_baseline_home_24)
    object Movie : Screen("movie", R.string.movie, R.drawable.ic_baseline_movie_24)
    object Add : Screen("add", R.string.add, R.drawable.ic_baseline_add_24)
    object Tv : Screen("tv", R.string.tv, R.drawable.ic_baseline_tv_24)
    object Save : Screen("save", R.string.save, R.drawable.ic_baseline_save_alt_24)
}