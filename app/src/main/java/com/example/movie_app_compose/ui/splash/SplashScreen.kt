package com.example.movie_app_compose.ui.splash

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.movie_app_compose.ui.theme.ColorPalette
import com.example.movie_app_compose.ui.theme.Green500
import com.example.movie_app_compose.ui.theme.MovieAppComposeTheme

class SplashScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieAppComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = ColorPalette.background) {
                    SplashScreenContent()
                }
            }
        }
    }
}

@Composable
fun SplashScreenContent(modifier: Modifier = Modifier) {
    ConstraintLayout {
        val logo = createRef()
        Text(
            text = "TMDB LOGO",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.constrainAs(logo){
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )
    }
}

//@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview2() {
    MovieAppComposeTheme {
        Surface(color = ColorPalette.background) {
            SplashScreenContent()
        }
    }
}