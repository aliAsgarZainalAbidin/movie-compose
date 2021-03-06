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
import com.example.movie_app_compose.ui.theme.DarkBlue900
import com.example.movie_app_compose.ui.theme.MovieAppComposeTheme

@Composable
fun LazyRowCommonItem(
    modifier: Modifier = Modifier,
    imageUrl: String = "",
    title: String = "",
    date: String = "",
    voteAverage: Float = 0f
) {
    val fullUrlImage = "${BuildConfig.BASE_IMAGE_URL}$imageUrl"
    val data = rememberImagePainter(
        data = fullUrlImage,
        builder = {
            error(R.color.darkblue)
            placeholder(R.color.darkblue)
            crossfade(true)
        })

    ConstraintLayout {
        val (tvTitle, tvReleaseDate, rating, surfaceImage) = createRefs()
        Surface(
            shape = RoundedCornerShape(8.dp),
            elevation = 2.dp,
            modifier = modifier
                .constrainAs(surfaceImage) {
                    top.linkTo(parent.top)
                }
        ) {
            Image(
                painter = data,
                contentDescription = null,
                modifier = modifier
                    .width(123.dp)
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
        }

        TextComponent(
            title,
            style = MaterialTheme.typography.subtitle1,
            fontWeight = FontWeight.Bold,
            modifier = modifier
                .constrainAs(tvTitle) {
                    linkTo(surfaceImage.start, surfaceImage.end)
                    top.linkTo(surfaceImage.bottom, 20.dp)
                }
                .width(123.dp)
        )

        TextComponent(
            date,
            style = MaterialTheme.typography.caption,
            modifier = modifier
                .constrainAs(tvReleaseDate) {
                    top.linkTo(tvTitle.bottom, 4.dp)
                    start.linkTo(surfaceImage.start)
                }
        )

        val progress by remember { mutableStateOf(voteAverage.div(10)) }
        val animatedProgress by animateFloatAsState(
            targetValue = progress,
            animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
        )

        ConstraintLayout(modifier = modifier.constrainAs(rating) {
            top.linkTo(surfaceImage.bottom)
            bottom.linkTo(surfaceImage.bottom)
            end.linkTo(surfaceImage.end, 4.dp)
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
fun PreviewLazyRowCommonItem() {
    MovieAppComposeTheme {
        LazyRowCommonItem()
    }
}