package com.example.movie_app_compose.ui.detail

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberImagePainter
import com.example.movie_app_compose.BuildConfig
import com.example.movie_app_compose.BuildConfig.TAG
import com.example.movie_app_compose.R
import com.example.movie_app_compose.model.Genre
import com.example.movie_app_compose.ui.components.Chip
import com.example.movie_app_compose.ui.components.TextComponent
import com.example.movie_app_compose.ui.theme.DarkBlue900
import com.example.movie_app_compose.ui.theme.Green500
import com.example.movie_app_compose.ui.theme.Grey
import com.example.movie_app_compose.ui.theme.MovieAppComposeTheme
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
    listGenre: List<Genre> = listOf(),
) {
    val image = rememberImagePainter(
        data = imageUrl,
        builder = {
            error(R.drawable.ic_baseline_image_not_supported_24)
            placeholder(R.color.darkblue)
            crossfade(true)
        })

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = DarkBlue900)
    ) {
        ConstraintLayout(
            modifier = modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            val (tvTitle, ivSurface, lzCategory, tvCategory) = createRefs()
            val (tvTitleOver, tvOverview, tvTitleDate, tvDate, tvTitleAdult, tvAdult, tvTitlePopularity, tvPopularity, tvTitleLanguage, tvLanguage) = createRefs()
            val backImage = painterResource(id = R.drawable.landscape)

            Surface(
                modifier = modifier
                    .height(272.dp)
                    .constrainAs(ivSurface) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                        height = Dimension.preferredWrapContent
                    },
                shape = RoundedCornerShape(16.dp)
            ) {
                ConstraintLayout {
                    val (ivBackdrop, view) = createRefs()
                    Image(
                        painter = image,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .constrainAs(ivBackdrop) {
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                            },
                        alignment = Alignment.Center
                    )

                    Spacer(modifier = modifier
                        .alpha(0.5f)
                        .background(DarkBlue900)
                        .height(48.dp)
                        .fillMaxWidth()
                        .constrainAs(view) {
                            bottom.linkTo(parent.bottom)
                        })
                }
            }

            Text(
                text = title,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.h6,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start,
                modifier = modifier
                    .constrainAs(tvTitle) {
                        bottom.linkTo(ivSurface.bottom, 12.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
                    .padding(horizontal = 16.dp)
            )
            TextComponent(
                value = titleDate,
                modifier = modifier.constrainAs(tvTitleDate) {
                    top.linkTo(ivSurface.bottom, 12.dp)
                    start.linkTo(parent.start)
                },
                style = MaterialTheme.typography.caption
            )
            TextComponent(
                value = date,
                modifier = modifier.constrainAs(tvDate) {
                    top.linkTo(tvTitleDate.bottom)
                    start.linkTo(tvTitleDate.start)
                },
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.caption,
                color = Grey
            )
            TextComponent(
                value = "Popularity",
                modifier = modifier.constrainAs(tvTitlePopularity) {
                    top.linkTo(tvDate.bottom, 12.dp)
                    start.linkTo(tvDate.start)
                },
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.caption
            )
            TextComponent(
                value = popularity,
                modifier = modifier.constrainAs(tvPopularity) {
                    top.linkTo(tvTitlePopularity.bottom)
                    start.linkTo(tvTitlePopularity.start)
                },
                style = MaterialTheme.typography.caption,
                color = Grey
            )
            val guideline = createGuidelineFromStart(0.5f)
            TextComponent(
                value = "Adult",
                modifier = modifier.constrainAs(tvTitleAdult) {
                    top.linkTo(tvTitleDate.top)
                    start.linkTo(guideline)
                },
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.caption
            )
            TextComponent(
                value = adult,
                modifier = modifier.constrainAs(tvAdult) {
                    top.linkTo(tvTitleAdult.bottom)
                    start.linkTo(tvTitleAdult.start)
                },
                style = MaterialTheme.typography.caption,
                color = Grey
            )
            TextComponent(
                value = "Language",
                modifier = modifier.constrainAs(tvTitleLanguage) {
                    top.linkTo(tvAdult.bottom, 12.dp)
                    start.linkTo(tvAdult.start)
                },
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.caption
            )
            TextComponent(
                value = language.uppercase(Locale.getDefault()),
                modifier = modifier.constrainAs(tvLanguage) {
                    top.linkTo(tvTitleLanguage.bottom)
                    start.linkTo(tvTitleLanguage.start)
                },
                style = MaterialTheme.typography.caption,
                color = Grey
            )

            TextComponent(
                value = "Genre",
                modifier = modifier.constrainAs(tvCategory) {
                    top.linkTo(tvPopularity.bottom, 12.dp)
                    start.linkTo(tvPopularity.start)
                },
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.caption
            )

            LazyRow(
                modifier = modifier.constrainAs(lzCategory) {
                    top.linkTo(tvCategory.bottom, 4.dp)
                    start.linkTo(tvCategory.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
            ) {
                items(listGenre.size) { index ->
                    Chip(text = listGenre.get(index).name.toString())
                }
            }

            TextComponent(
                value = "Overview",
                modifier = modifier.constrainAs(tvTitleOver) {
                    top.linkTo(lzCategory.bottom, 12.dp)
                    start.linkTo(lzCategory.start)
                },
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.subtitle1,
            )
            Spacer(
                modifier = modifier
                    .height(8.dp)
                    .fillMaxWidth()
            )
            TextComponent(
                value = overview,
                modifier = modifier.constrainAs(tvOverview) {
                    top.linkTo(tvTitleOver.bottom)
                    start.linkTo(tvTitleOver.start)
                },
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

@Preview
@Composable
fun PreviewDetailContent() {
    MovieAppComposeTheme {
        DetailContent(
            title = "Jetpack Compose",
            popularity = "1000",
            language = "EN",
            overview = "Hallo World",
            adult = "YES",
            titleDate = "Release Date",
            imageUrl = "${BuildConfig.BASE_IMAGE_URL}/j28p5VwI5ieZnNwfeuZ5Ve3mPsn.jpg",
            date = "18-08-1999"
        )
    }
}