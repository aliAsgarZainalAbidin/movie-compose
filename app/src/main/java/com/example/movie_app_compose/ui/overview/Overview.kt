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
import com.example.movie_app_compose.ui.components.LazyRowItem
import com.example.movie_app_compose.ui.components.LazyRowPopularItem
import com.example.movie_app_compose.ui.theme.MovieAppComposeTheme

@Composable
fun OverviewBody(modifier: Modifier = Modifier, scrollState : ScrollState) {
    Column(modifier = modifier.verticalScroll(scrollState)) {
        ConstraintLayout(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            val (tvWelcome, lzRow, tvPopular, lzRowPopular) = createRefs()
            Text(
                buildAnnotatedString {
                    withStyle(style = SpanStyle(fontSize = 24.sp,fontWeight = FontWeight.Bold)) {
                        append("Trending\n")
                    }
                    withStyle(style = SpanStyle(fontSize = 11.sp)) {
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
                    withStyle(style = SpanStyle(fontSize = 20.sp,fontWeight = FontWeight.Bold)) {
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
