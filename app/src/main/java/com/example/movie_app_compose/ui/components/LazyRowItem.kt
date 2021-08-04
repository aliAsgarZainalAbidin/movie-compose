package com.example.movie_app_compose.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

@Composable
fun LazyRowItem(modifier: Modifier = Modifier) {
    ConstraintLayout {
        val (image, tvTitle, tvReleaseDate, rating, surfaceImage) = createRefs()
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
                    top.linkTo(surfaceImage.bottom, 8.dp)
                }
                .width(184.dp)
        )

        TextComponent(
            "19-08-2021",
            style = MaterialTheme.typography.caption,
            modifier = modifier
                .constrainAs(tvReleaseDate) {
                    top.linkTo(tvTitle.bottom, 8.dp)
                    start.linkTo(surfaceImage.start)
                }
        )
    }
}

@Preview
@Composable
fun PreviewLazyRowItem() {
    MovieAppComposeTheme {
        LazyRowItem()
    }
}