package com.example.movie_app_compose.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberImagePainter
import com.example.movie_app_compose.ui.components.Chip
import com.example.movie_app_compose.ui.components.TextComponent
import com.example.movie_app_compose.ui.theme.DarkBlue900
import com.example.movie_app_compose.ui.theme.Grey
import java.util.*

@Composable
fun DetailContent(
    modifier: Modifier = Modifier,
    type: String = "",
    title: String = "",
    imageUrl: String = "",
    titleDate: String = "",
    date: String = "",
    popularity: String = "",
    adult: String = "",
    language: String = "",
    overview: String = "",
    listGenre: List<String> = listOf(),
) {
    val image = rememberImagePainter(data = imageUrl)
    Image(
        painter = image,
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
                    painter = image,
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
                    text = title,
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
                            value = titleDate,
                            modifier = modifier,
                            style = MaterialTheme.typography.caption
                        )
                        TextComponent(
                            value = date,
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
                            value = popularity,
                            modifier = modifier,
                            style = MaterialTheme.typography.caption,
                            color = Grey
                        )
                    }
                    Spacer(modifier = modifier.height(4.dp))
                    Row {
                        TextComponent(
                            value = "Adult : ",
                            modifier = modifier,
                            style = MaterialTheme.typography.caption
                        )
                        TextComponent(
                            value = adult,
                            modifier = modifier,
                            style = MaterialTheme.typography.caption,
                            color = Grey
                        )
                    }
                    Spacer(modifier = modifier.height(4.dp))
                    Row {
                        TextComponent(
                            value = "Language : ",
                            modifier = modifier,
                            style = MaterialTheme.typography.caption
                        )
                        TextComponent(
                            value = language.uppercase(Locale.getDefault()),
                            modifier = modifier,
                            style = MaterialTheme.typography.caption,
                            color = Grey
                        )
                    }
                    Spacer(modifier = modifier.height(8.dp))
                    LazyRow {
                        items(listGenre.size) { index ->
                            Chip(text = listGenre.get(index))
                        }
                    }
                    Spacer(modifier = modifier.height(8.dp))
                }

                Column(
                    modifier = modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .constrainAs(columnOverview) {
                            top.linkTo(ivPoster.bottom, 16.dp)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.fillToConstraints
                        },
                    verticalArrangement = Arrangement.Bottom
                ) {
                    TextComponent(
                        value = "Overview",
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
                        value = overview,
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