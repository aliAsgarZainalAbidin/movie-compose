package com.example.movie_app_compose.ui.save.tab

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.movie_app_compose.ui.components.LazyColumnItem
import com.example.movie_app_compose.ui.theme.DarkBlue900
import com.example.movie_app_compose.ui.theme.MovieAppComposeTheme

@Composable
fun TabTvShow(){
    LazyColumn(modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)){
        items(5){
            LazyColumnItem()
        }
    }
}

@Preview (showSystemUi = true)
@Composable
fun PreviewTabTvShow(){
    MovieAppComposeTheme{
        Surface(color = DarkBlue900) {
            TabTvShow()
        }
    }
}