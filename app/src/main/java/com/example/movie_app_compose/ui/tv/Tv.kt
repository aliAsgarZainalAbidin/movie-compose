package com.example.movie_app_compose.ui.tv

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movie_app_compose.BuildConfig
import com.example.movie_app_compose.api.ApiFactory
import com.example.movie_app_compose.api.ApiInterface
import com.example.movie_app_compose.data.AppDatabase
import com.example.movie_app_compose.data.Repository
import com.example.movie_app_compose.ui.IdleContent
import com.example.movie_app_compose.ui.components.LazyRowCommonItem
import com.example.movie_app_compose.ui.components.LazyRowItem
import com.example.movie_app_compose.ui.components.LazyRowLandscapeItem
import com.example.movie_app_compose.ui.theme.Green500
import com.example.movie_app_compose.ui.theme.MovieAppComposeTheme
import com.example.movie_app_compose.util.Const

@Composable
fun Tv(
    modifier: Modifier = Modifier,
    scrollState: ScrollState,
    onItemClickListener: (String, String) -> Unit = { t: String, i: String -> }
) {
    val restApi by lazy { ApiFactory.create() }
    val appDatabase = AppDatabase.getDatabase(LocalContext.current)
    val tvViewModel: TvViewModel = viewModel()
    tvViewModel.repository = Repository(apiInterface = restApi, appDatabase = appDatabase)
    val listOnTheAir = tvViewModel.getOnTheAir().observeAsState()
    val listAiringToday = tvViewModel.getAiringToday().observeAsState()
    val listPopularTv = tvViewModel.getPopularTv().observeAsState()

    if (listOnTheAir.value != null &&
        listAiringToday.value != null &&
        listPopularTv.value != null
    ) {
        Column(
            modifier = modifier
                .verticalScroll(scrollState)
                .fillMaxHeight()
        ) {
            ConstraintLayout(
                modifier = modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                val (tvWelcome, lzRow, tvPopular, lzRowPopular, tvMovies, lzRowMovies, spacer) = createRefs()
                Text(
                    buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Green500
                            )
                        ) {
                            append("On The Air")
                        }
                    },
                    modifier = modifier.constrainAs(tvWelcome) {
                        top.linkTo(parent.top, 16.dp)
                        start.linkTo(parent.start, 16.dp)
                        width = Dimension.preferredWrapContent
                    }
                )

                LazyRow(
                    modifier = modifier.constrainAs(lzRow) {
                        top.linkTo(tvWelcome.bottom, 8.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    items(listOnTheAir.value?.size ?: 0) { index ->
                        val data = listOnTheAir.value?.get(index)
                        val title = data?.name ?: ""
                        val imageUrl = data?.backdropPath ?: ""
                        val date = data?.firstAirDate ?: ""
                        val voteAverage = data?.voteAverage ?: 0f
                        LazyRowLandscapeItem(
                            modifier = modifier.clickable {
                                onItemClickListener(Const.TYPE_TV, data?.id.toString())
                            },
                            imageUrl = imageUrl,
                            title = title,
                            date = date,
                            voteAverage = voteAverage
                        )
                    }
                }

                Text(
                    buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Green500
                            )
                        ) {
                            append("Tv Airing Today")
                        }
                    },
                    modifier = modifier.constrainAs(tvMovies) {
                        top.linkTo(lzRow.bottom, 16.dp)
                        start.linkTo(parent.start, 16.dp)
                        width = Dimension.preferredWrapContent
                    }
                )

                LazyRow(
                    modifier = modifier.constrainAs(lzRowMovies) {
                        top.linkTo(tvMovies.bottom, 8.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    items(listAiringToday.value?.size ?: 0) { index ->
                        val data = listAiringToday.value?.get(index)
                        val title = data?.name ?: ""
                        val imageUrl = data?.backdropPath ?: ""
                        val date = data?.firstAirDate ?: ""
                        val voteAverage = data?.voteAverage ?: 0f
                        LazyRowLandscapeItem(
                            modifier = modifier.clickable {
                                onItemClickListener(Const.TYPE_TV, data?.id.toString())
                            },
                            imageUrl = imageUrl,
                            title = title,
                            date = date,
                            voteAverage = voteAverage
                        )
                    }
                }

                Text(
                    buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Green500
                            )
                        ) {
                            append("Popular Tv Shows")
                        }
                    },
                    modifier = modifier.constrainAs(tvPopular) {
                        top.linkTo(lzRowMovies.bottom, 16.dp)
                        start.linkTo(parent.start, 16.dp)
                        width = Dimension.preferredWrapContent
                    }
                )

                LazyRow(
                    modifier = modifier.constrainAs(lzRowPopular) {
                        top.linkTo(tvPopular.bottom, 8.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    items(listPopularTv.value?.size ?: 0) { index ->
                        val data = listPopularTv.value?.get(index)
                        val title = data?.name ?: ""
                        val imageUrl = data?.posterPath ?: ""
                        val date = data?.firstAirDate ?: ""
                        val voteAverage = data?.voteAverage ?: 0f
                        LazyRowCommonItem(
                            modifier = modifier.clickable {
                                onItemClickListener(Const.TYPE_TV, data?.id.toString())
                            },
                            imageUrl = imageUrl,
                            title = title,
                            date = date,
                            voteAverage = voteAverage
                        )
                    }
                }

                Spacer(modifier = modifier
                    .width(16.dp)
                    .constrainAs(spacer) {
                        top.linkTo(lzRowPopular.bottom, margin = 16.dp)
                    })
            }
        }
    } else {
        IdleContent()
    }
}

@Preview
@Composable
fun PreviewTv() {
    MovieAppComposeTheme {
        Tv(scrollState = rememberScrollState())
    }
}