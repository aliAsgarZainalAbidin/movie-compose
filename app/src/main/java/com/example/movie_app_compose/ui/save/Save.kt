package com.example.movie_app_compose.ui.save

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.movie_app_compose.ui.components.TextComponent
import com.example.movie_app_compose.ui.movie.Movie
import com.example.movie_app_compose.ui.overview.OverviewBody
import com.example.movie_app_compose.ui.save.tab.TabMovies
import com.example.movie_app_compose.ui.save.tab.TabTvShow
import com.example.movie_app_compose.ui.theme.MovieAppComposeTheme

@Composable
fun SaveMenu(modifier: Modifier = Modifier) {
    var scrollState : ScrollState
    var state by remember { mutableStateOf(0) }
    val titles = listOf<String>("Movies", "Tv Shows")

    Column(
        modifier = modifier
            .fillMaxHeight()
    ) {
        TabRow(selectedTabIndex = state) {
            titles.forEachIndexed { index, s ->
                Tab(
                    text = { TextComponent(value = s) },
                    selected = state == index,
                    onClick = { state = index })
            }
        }
        when (state) {
            0 -> {
                TabMovies()
            }
            1 -> {
                TabTvShow()
            }
        }
    }
}

@Preview
@Composable
fun PreviewSaveMenu() {
    MovieAppComposeTheme {
        SaveMenu()
    }
}