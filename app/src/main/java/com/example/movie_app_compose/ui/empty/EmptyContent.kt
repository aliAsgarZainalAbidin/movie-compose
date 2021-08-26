package com.example.movie_app_compose.ui.empty

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.movie_app_compose.R
import com.example.movie_app_compose.data.entity.MyMovie
import com.example.movie_app_compose.data.entity.MyTvShow
import com.example.movie_app_compose.ui.theme.DarkBlue900
import com.example.movie_app_compose.ui.theme.Green500
import com.example.movie_app_compose.util.Const

@Composable
fun EmptyContent(modifier: Modifier = Modifier) {
    val animationSpec by
    rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.empty))

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(DarkBlue900)
    ) {
        var (box, title, subtitle) = createRefs()

        LottieAnimation(
            animationSpec,
            alignment = Alignment.Center,
            modifier = modifier
                .constrainAs(box) {
                    centerHorizontallyTo(parent)
                    top.linkTo(parent.top, 150.dp)
                }
                .size(200.dp)
        )

        Text(
            text = "Data Kosong",
            modifier = modifier.constrainAs(title) {
                top.linkTo(box.bottom)
                start.linkTo(box.start)
                end.linkTo(box.end)
            },
            style = MaterialTheme.typography.h6,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Yuk simpan movie dan tv show \nfavorite Anda",
            modifier = modifier.constrainAs(subtitle) {
                top.linkTo(title.bottom)
                start.linkTo(box.start)
                end.linkTo(box.end)
            },
            style = MaterialTheme.typography.subtitle1,
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewIdleContent() {
    androidx.compose.material.Surface(color = DarkBlue900) {
        EmptyContent()
    }
}