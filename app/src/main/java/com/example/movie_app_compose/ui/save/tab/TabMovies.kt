package com.example.movie_app_compose.ui.save.tab

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.movie_app_compose.navigation.MainNavigation
import com.example.movie_app_compose.ui.components.GridLayout
import com.example.movie_app_compose.ui.components.LazyColumnItem
import com.example.movie_app_compose.ui.detail.Detail
import com.example.movie_app_compose.ui.theme.DarkBlue900
import com.example.movie_app_compose.ui.theme.MovieAppComposeTheme

val topics = listOf(
    "Arts & Crafts", "Beauty", "Books", "Business", "Comics", "Culinary",
    "Design", "Fashion", "Film", "History", "Maths", "Music", "People", "Philosophy",
    "Religion", "Social sciences", "Technology", "TV", "Writing"
)

@Composable
fun TabMovies(navController: NavController) {
    LazyColumn(modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)) {
        items(4) {
            LazyColumnItem(modifier = Modifier.clickable {
                navController.navigate(MainNavigation.Detail.router)
            })
        }
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