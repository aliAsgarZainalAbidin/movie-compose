package com.example.movie_app_compose.ui.overview

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
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
import com.example.movie_app_compose.api.ApiFactory
import com.example.movie_app_compose.data.AppDatabase
import com.example.movie_app_compose.data.Repository
import com.example.movie_app_compose.ui.components.LazyRowCommonItem
import com.example.movie_app_compose.ui.components.LazyRowItem
import com.example.movie_app_compose.ui.components.LazyRowLandscapeItem
import com.example.movie_app_compose.ui.components.LazyRowPopularItem
import com.example.movie_app_compose.ui.theme.Green500
import com.example.movie_app_compose.ui.theme.Green600
import com.example.movie_app_compose.ui.theme.MovieAppComposeTheme

@Composable
fun OverviewBody(modifier: Modifier = Modifier, scrollState: ScrollState) {
    val restApi by lazy { ApiFactory.create() }
    var overviewViewModel: OverviewViewModel = viewModel()
    overviewViewModel.repositor = Repository(restApi, AppDatabase.getDatabase(LocalContext.current))

    var listPeople = overviewViewModel.getPopularPeople().observeAsState()
    var listTrending = overviewViewModel.getTrendingMovies().observeAsState()
    var listOnTheAir = overviewViewModel.getOnTheAir().observeAsState()

    Column(
        modifier = modifier
            .verticalScroll(
                scrollState,
                flingBehavior = ScrollableDefaults.flingBehavior()
            )
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
                        append("Trending")
                    }
                },
                modifier = modifier.constrainAs(tvWelcome) {
                    top.linkTo(lzRowPopular.bottom, 16.dp)
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
                items(listTrending.value?.size ?: 0) { index ->
                    listTrending.value?.get(index)?.let { LazyRowItem(movie = it) }
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
                        append("Popular People\n")
                    }
                    withStyle(style = SpanStyle(fontSize = 11.sp, color = Green600)) {
                        append("Millions of movies, Tv Shows and people to discover")
                    }
                },
                modifier = modifier.constrainAs(tvPopular) {
                    top.linkTo(parent.top, 16.dp)
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
                items(listPeople.value?.size ?: 0) { index ->
                    listPeople.value?.let {
                        LazyRowPopularItem(people = it.get(index = index))
                    }
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
                        append("On The Air")
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
                items(listOnTheAir.value?.size ?: 0) {index ->
                    LazyRowLandscapeItem(onTheAir = listOnTheAir.value?.get(index))
                }
            }

            Spacer(modifier = modifier
                .width(16.dp)
                .constrainAs(spacer) {
                    top.linkTo(lzRowMovies.bottom, margin = 16.dp)
                })
        }
    }
}

@Preview
@Composable
fun PreviewOverviewBody() {
    MovieAppComposeTheme {
        Surface {
            OverviewBody(scrollState = rememberScrollState())
        }
    }
}
