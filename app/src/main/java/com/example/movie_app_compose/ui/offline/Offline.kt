package com.example.movie_app_compose.ui.offline

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.movie_app_compose.R
import com.example.movie_app_compose.ui.components.TextComponent
import com.example.movie_app_compose.ui.theme.DarkBlue900

@Composable
fun OfflineContent(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
            .fillMaxHeight()
            .fillMaxWidth(),
        color = DarkBlue900
    ) {
        ConstraintLayout(
            modifier = modifier,
        ) {
            val (image, tvConnection, tvDesc) = createRefs()
            Image(
                painter = painterResource(id = R.drawable.ic_no_signal),
                contentDescription = null,
                modifier = modifier
                    .padding(8.dp)
                    .size(100.dp)
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )
            TextComponent(
                value = "Koneksi internetmu terganggu",
                fontWeight = FontWeight.Bold,
                color = Color.White,
                style = MaterialTheme.typography.h6,
                textAlign = TextAlign.Center,
                modifier = modifier.constrainAs(tvConnection) {
                    top.linkTo(image.bottom, 16.dp)
                    start.linkTo(image.start)
                    end.linkTo(image.end)
                }
            )
            TextComponent(
                value = "Yuk, pastikan internetmu lancar dengan \n cek ulang paket data, WiFi, atau jaringan di\n tempatmu",
                color = Color.White,
                maxLines = 3,
                textAlign = TextAlign.Center,
                modifier = modifier.constrainAs(tvDesc) {
                    top.linkTo(tvConnection.bottom, 4.dp)
                    start.linkTo(tvConnection.start)
                    end.linkTo(tvConnection.end)
                }
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewOfflineContent() {
    androidx.compose.material.Surface(color = DarkBlue900) {
        OfflineContent()
    }
}