package com.example.movie_app_compose.ui.save.tab

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
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
import com.example.movie_app_compose.navigateToDetail
import com.example.movie_app_compose.ui.components.LazyColumnItem
import com.example.movie_app_compose.ui.empty.EmptyContent
import com.example.movie_app_compose.ui.theme.DarkBlue900
import com.example.movie_app_compose.ui.theme.MovieAppComposeTheme

@Composable
fun TabTvShow(navController: NavController) {
    val restApi by lazy { ApiFactory.create() }
    val tabTvShowViewModel: TabTvShowViewModel = viewModel()
    tabTvShowViewModel.repository =
        Repository(restApi, AppDatabase.getDatabase(LocalContext.current))
    val listTvShow = tabTvShowViewModel.getAllTvShow().observeAsState()

    if (listTvShow.value?.size ?: 0 > 0){
        LazyColumn(modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)) {
            items(listTvShow.value?.size ?: 0) { index ->
                val data = listTvShow.value?.get(index)
                LazyColumnItem(
                    imageUrl = data?.posterPath ?: "",
                    title = data?.name ?: "",
                    date = data?.firstAirDate ?: "",
                    overview = data?.overview ?: "",
                    modifier = Modifier.clickable {
                        navigateToDetail(navController, data?.type.toString(), data?.id.toString(), data?.typeRepo.toString())
                    },
                    typeRepo = data?.typeRepo.toString()
                )
            }
        }
    } else {
        EmptyContent()
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewTabTvShow() {
    MovieAppComposeTheme {
        Surface(color = DarkBlue900) {
            TabTvShow(rememberNavController())
        }
    }
}