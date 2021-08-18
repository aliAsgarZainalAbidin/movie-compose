package com.example.movie_app_compose.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberImagePainter
import com.example.movie_app_compose.BuildConfig
import com.example.movie_app_compose.R
import com.example.movie_app_compose.model.TvShow
import com.example.movie_app_compose.model.entity.OnTheAir
import com.example.movie_app_compose.ui.theme.DarkBlue900
import com.example.movie_app_compose.ui.theme.MovieAppComposeTheme

@Composable
fun LazyRowLandscapeItem(modifier: Modifier = Modifier, onTheAir: OnTheAir? = null) {
    ConstraintLayout(modifier = modifier) {
        val (tvTitle, tvReleaseDate, rating, surfaceImage) = createRefs()

        Surface(
            shape = RoundedCornerShape(4.dp),
            modifier = modifier.constrainAs(surfaceImage) {
                top.linkTo(parent.top)
            }) {
            Image(
                painter = rememberImagePainter(
                    data = "${BuildConfig.BASE_IMAGE_URL}${onTheAir?.backdropPath}"
                ),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .width(200.dp)
                    .height(123.dp)
            )
        }

        TextComponent(
            "${onTheAir?.name}",
            style = MaterialTheme.typography.subtitle1,
            fontWeight = FontWeight.Bold,
            modifier = modifier
                .constrainAs(tvTitle) {
                    start.linkTo(surfaceImage.start)
                    top.linkTo(surfaceImage.bottom, 20.dp)
                }
                .width(200.dp)
        )

        TextComponent(
            "${onTheAir?.firstAirDate}",
            style = MaterialTheme.typography.caption,
            modifier = modifier
                .constrainAs(tvReleaseDate) {
                    top.linkTo(tvTitle.bottom, 4.dp)
                    start.linkTo(surfaceImage.start)
                }
        )

        val progress by remember { mutableStateOf(onTheAir?.voteAverage?.div(10)?.toFloat() ?: 0f) }
        val animatedProgress by animateFloatAsState(
            targetValue = progress,
            animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
        )

        ConstraintLayout(modifier = modifier.constrainAs(rating) {
            top.linkTo(surfaceImage.bottom)
            bottom.linkTo(surfaceImage.bottom)
            end.linkTo(surfaceImage.end, 2.dp)
        }) {
            val (tvProgress, CPIndicator) = createRefs()
            Surface(
                modifier = modifier.constrainAs(CPIndicator) {}, color = DarkBlue900,
                shape = CircleShape
            ) {
                CircularProgressIndicator(
                    progress = animatedProgress, strokeWidth = 3.dp,
                    modifier = modifier
                )
            }

            TextComponent(
                value = "${animatedProgress.times(100).toInt()}%", modifier = modifier
                    .constrainAs(tvProgress) {
                        centerVerticallyTo(CPIndicator)
                        centerHorizontallyTo(CPIndicator)
                    }
                    .padding(16.dp),
                style = MaterialTheme.typography.caption,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview
@Composable
fun PreviewLazyRowLandscapeItem() {
    MovieAppComposeTheme {
        LazyRowLandscapeItem()
    }
}