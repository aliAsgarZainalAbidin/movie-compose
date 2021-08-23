package com.example.movie_app_compose.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.movie_app_compose.ui.theme.DarkBlue900
import com.example.movie_app_compose.ui.theme.Green500

@Composable
fun IdleContent(modifier: Modifier = Modifier) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(DarkBlue900)
    ) {
        var loading = createRef()
        CircularProgressIndicator(
            modifier = modifier.constrainAs(loading){
                centerTo(parent)
            },
            color = Green500
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewIdleContent() {
    androidx.compose.material.Surface(color = DarkBlue900) {
        IdleContent()
    }
}