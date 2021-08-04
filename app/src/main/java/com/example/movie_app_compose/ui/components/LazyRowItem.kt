package com.example.movie_app_compose.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.movie_app_compose.R
import com.example.movie_app_compose.ui.theme.MovieAppComposeTheme
import com.example.movie_app_compose.ui.theme.*

@Composable
fun LazyRowItem(modifier: Modifier = Modifier) {
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
                painter = painterResource(R.drawable.sample_foto),
                contentDescription = null,
                modifier = modifier
                    .width(184.dp)
                    .height(300.dp),
                contentScale = ContentScale.Crop
            )
        }

        TextComponent(
            "Fast and Furios Nine asdasdasd asdasdas as",
            style = MaterialTheme.typography.subtitle1,
            fontWeight = FontWeight.Bold,
            modifier = modifier
                .constrainAs(tvTitle) {
                    linkTo(surfaceImage.start, surfaceImage.end)
                    top.linkTo(surfaceImage.bottom, 20.dp)
                }
                .width(184.dp)
        )

        TextComponent(
            "19-08-2021",
            style = MaterialTheme.typography.caption,
            modifier = modifier
                .constrainAs(tvReleaseDate) {
                    top.linkTo(tvTitle.bottom, 4.dp)
                    start.linkTo(surfaceImage.start)
                }
        )

        val progress by remember { mutableStateOf(0.78f) }
        val animatedProgress by animateFloatAsState(
            targetValue = progress,
            animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
        )

//        CircularProgressIndicatorComponent(
//            animatedProgress = animatedProgress,
//            modifier = modifier.constrainAs(rating) {
//                top.linkTo(surfaceImage.top)
//                bottom.linkTo(surfaceImage.bottom)
//                end.linkTo(surfaceImage.end, 8.dp)
//            }
//        )

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
fun PreviewLazyRowItem() {
    MovieAppComposeTheme {
        LazyRowItem()
    }
}