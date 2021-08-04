package com.example.movie_app_compose.ui.overview

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.movie_app_compose.ui.components.LazyRowCommonItem
import com.example.movie_app_compose.ui.components.LazyRowItem
import com.example.movie_app_compose.ui.components.LazyRowLandscapeItem
import com.example.movie_app_compose.ui.components.LazyRowPopularItem
import com.example.movie_app_compose.ui.theme.Green500
import com.example.movie_app_compose.ui.theme.Green600
import com.example.movie_app_compose.ui.theme.MovieAppComposeTheme

@Composable
fun OverviewBody(modifier: Modifier = Modifier, scrollState: ScrollState) {
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
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Green500
                        )
                    ) {
                        append("Trending\n")
                    }
                    withStyle(style = SpanStyle(fontSize = 11.sp, color = Green600)) {
                        append("Millions of movies, Tv Shows and people to discover")
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
                items(4) {
                    LazyRowItem()
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
                        append("Popular People")
                    }
                },
                modifier = modifier.constrainAs(tvPopular) {
                    top.linkTo(lzRow.bottom, 16.dp)
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
                items(4) {
                    LazyRowPopularItem()
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
                    top.linkTo(lzRowPopular.bottom, 16.dp)
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
                items(4) {
                    LazyRowLandscapeItem()
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
