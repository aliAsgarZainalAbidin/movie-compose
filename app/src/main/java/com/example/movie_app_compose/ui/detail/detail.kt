package com.example.movie_app_compose.ui.detail

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.movie_app_compose.R
import com.example.movie_app_compose.ui.components.Chip
import com.example.movie_app_compose.ui.components.TextComponent
import com.example.movie_app_compose.ui.theme.DarkBlue900
import com.example.movie_app_compose.ui.theme.Grey
import com.example.movie_app_compose.ui.theme.MovieAppComposeTheme

@Composable
fun Detail(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.sample_foto),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .fillMaxHeight()
            .fillMaxWidth()
    )
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .alpha(0.8f)
            .background(color = DarkBlue900)
    ) {
        Column(
            modifier = modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            ConstraintLayout(
                modifier = modifier
                    .fillMaxWidth()
            ) {
                val (ivPoster, tvTitle, columnDetail, columnOverview, consCircular) = createRefs()
                Image(
                    painter = painterResource(id = R.drawable.sample_foto),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = modifier
                        .width(123.dp)
                        .height(200.dp)
                        .constrainAs(ivPoster) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                        }
                )

                Text(
                    text = "Fast and Furios as",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.h6,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Start,
                    modifier = modifier.constrainAs(tvTitle) {
                        top.linkTo(ivPoster.top)
                        start.linkTo(ivPoster.end, margin = 16.dp)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
                )

                Column(modifier = modifier.constrainAs(columnDetail) {
                    top.linkTo(tvTitle.bottom, 16.dp)
                    start.linkTo(tvTitle.start)
                    width = Dimension.wrapContent
                }) {
                    Row {
                        TextComponent(
                            value = "Release Date : ",
                            modifier = modifier,
                            style = MaterialTheme.typography.caption
                        )
                        TextComponent(
                            value = "2021-07-28",
                            modifier = modifier,
                            style = MaterialTheme.typography.caption,
                            color = Grey
                        )
                    }
                    Spacer(modifier = modifier.height(4.dp))
                    Row {
                        TextComponent(
                            value = "Popularity : ",
                            modifier = modifier,
                            style = MaterialTheme.typography.caption
                        )
                        TextComponent(
                            value = "11135.763",
                            modifier = modifier,
                            style = MaterialTheme.typography.caption,
                            color = Grey
                        )
                    }
                    Spacer(modifier = modifier.height(8.dp))
                    LazyRow {
                        items(3) {
                            Chip(text = "Action")
                        }
                    }
                    Spacer(modifier = modifier.height(8.dp))
                }

                Column(
                    modifier = modifier
                        .constrainAs(columnOverview) {
                            top.linkTo(ivPoster.bottom, 16.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.fillToConstraints
                        }
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    TextComponent(
                        value = "OverView",
                        modifier = modifier,
                        style = MaterialTheme.typography.subtitle1,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(
                        modifier = modifier
                            .height(8.dp)
                            .fillMaxWidth()
                    )
                    TextComponent(
                        value = "Supervillains Harley Quinn, Bloodsport, Peacemaker and a collection of nutty cons at Belle Reve prison join the super-secret, super-shady Task Force X as they are dropped off at the remote, enemy-infused island of Corto Maltese.",
                        modifier = modifier,
                        style = MaterialTheme.typography.caption,
                        maxLines = Int.MAX_VALUE,
                        textAlign = TextAlign.Justify
                    )
                    Spacer(
                        modifier = modifier
                            .height(16.dp)
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewDetail() {
    MovieAppComposeTheme {
        Surface(color = DarkBlue900) {
            Detail()
        }
    }
}