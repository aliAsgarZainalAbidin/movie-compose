package com.example.movie_app_compose.ui.splash

import android.Manifest
import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.movie_app_compose.R
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
    ConstraintLayout(
        modifier = modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        val logo = createRef()
        Image(
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = null,
            modifier = Modifier
                .constrainAs(logo) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    centerVerticallyTo(parent)
                }
                .width(150.dp)
                .height(150.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    MovieAppComposeTheme {
        Surface(color = ColorPalette.background) {
            SplashScreenContent()
        }
    }
}