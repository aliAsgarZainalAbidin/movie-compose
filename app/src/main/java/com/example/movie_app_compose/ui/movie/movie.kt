package com.example.movie_app_compose.ui.movie

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollScope
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
import androidx.dynamicanimation.animation.FlingAnimation
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movie_app_compose.BuildConfig
import com.example.movie_app_compose.api.ApiFactory
import com.example.movie_app_compose.data.AppDatabase
import com.example.movie_app_compose.data.Repository
import com.example.movie_app_compose.ui.IdleContent
import com.example.movie_app_compose.ui.components.LazyRowCommonItem
import com.example.movie_app_compose.ui.components.LazyRowItem
import com.example.movie_app_compose.ui.components.LazyRowLandscapeItem
import com.example.movie_app_compose.ui.components.LazyRowPopularItem
import com.example.movie_app_compose.ui.theme.Green500
import com.example.movie_app_compose.ui.theme.Green600
import com.example.movie_app_compose.ui.theme.MovieAppComposeTheme
import com.example.movie_app_compose.util.Const

@Composable
fun Movie(
    modifier: Modifier = Modifier,
    scrollState: ScrollState,
    onItemClickListener: (String, String) -> Unit = { t: String, i: String -> }
) {
    val restApi by lazy { ApiFactory.create() }
    val movieViewModel: MovieViewModel = viewModel()
    movieViewModel.repository = Repository(
        apiInterface = restApi,
        appDatabase = AppDatabase.getDatabase(
            LocalContext.current
        )
    )

    val listNowPlaying = movieViewModel.getPlayingMovies().observeAsState()
    val listUpcoming = movieViewModel.getUpcomingMovies().observeAsState()
    val listPopularMovies = movieViewModel.getPopularMovies().observeAsState()

    if (listNowPlaying.value != null &&
        listUpcoming.value != null &&
        listPopularMovies.value != null
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
                            append("Now Playing")
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
                    items(listNowPlaying.value?.size ?: 0) { index ->
                        val data = listNowPlaying.value?.get(index)
                        val title = data?.title ?: ""
                        val imageUrl = data?.posterPath ?: ""
                        val date = data?.releaseDate ?: ""
                        val voteAverage = data?.voteAverage ?: 0f
                        LazyRowItem(
                            modifier = modifier.clickable {
                                onItemClickListener(Const.TYPE_MOVIE, data?.id.toString())
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
                            append("Upcoming")
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
                    items(listUpcoming.value?.size ?: 0) { index ->
                        val data = listUpcoming.value?.get(index)
                        val title = data?.title ?: ""
                        val imageUrl = data?.backdropPath ?: ""
                        val date = data?.releaseDate ?: ""
                        val voteAverage = data?.voteAverage ?: 0f
                        LazyRowLandscapeItem(
                            modifier = modifier.clickable {
                                onItemClickListener(Const.TYPE_MOVIE, data?.id.toString())
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
                            append("Popular Movies")
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
                    items(listPopularMovies.value?.size ?: 0) { index ->
                        val data = listPopularMovies.value?.get(index)
                        val title = data?.title ?: ""
                        val imageUrl = data?.posterPath ?: ""
                        val date = data?.releaseDate ?: ""
                        val voteAverage = data?.voteAverage ?: 0f
                        LazyRowCommonItem(
                            modifier = modifier.clickable {
                                onItemClickListener(Const.TYPE_MOVIE, data?.id.toString())
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
fun PreviewMovie() {
    MovieAppComposeTheme {
        Movie(scrollState = rememberScrollState())
    }
}