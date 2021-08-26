package com.example.movie_app_compose.ui.save.tab

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.movie_app_compose.api.ApiFactory
import com.example.movie_app_compose.data.AppDatabase
import com.example.movie_app_compose.data.Repository
import com.example.movie_app_compose.navigation.Navigation
import com.example.movie_app_compose.ui.components.LazyColumnItem
import com.example.movie_app_compose.ui.empty.EmptyContent
import com.example.movie_app_compose.ui.theme.DarkBlue900
import com.example.movie_app_compose.ui.theme.MovieAppComposeTheme

val topics = listOf(
    "Arts & Crafts", "Beauty", "Books", "Business", "Comics", "Culinary",
    "Design", "Fashion", "Film", "History", "Maths", "Music", "People", "Philosophy",
    "Religion", "Social sciences", "Technology", "TV", "Writing"
)

@Composable
fun TabMovies(navController: NavController) {
    val restApi by lazy { ApiFactory.create() }
    val tabMovieViewModel: TabMovieViewModel = viewModel()
    tabMovieViewModel.repository =
        Repository(restApi, AppDatabase.getDatabase(LocalContext.current))
    val listMovies = tabMovieViewModel.getAllMyMovies().observeAsState()

    if (listMovies.value?.size ?: 0 > 0) {
        LazyColumn(modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)) {
            items(listMovies.value?.size ?: 0) { index ->
                val data = listMovies.value?.get(index)
                LazyColumnItem(
                    imageUrl = data?.posterPath ?: "",
                    title = data?.title ?: "",
                    date = data?.releaseDate ?: "",
                    overview = data?.overview ?: ""
                )
            }
        }
    } else {
        EmptyContent()
    }
}


@Preview(showSystemUi = true)
@Composable
fun PreviewTabMovies() {
    MovieAppComposeTheme {
        Surface(color = DarkBlue900) {
            TabMovies(navController = rememberNavController())
        }
    }
}