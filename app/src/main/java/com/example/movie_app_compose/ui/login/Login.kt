package com.example.movie_app_compose.ui.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import com.example.movie_app_compose.R
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.movie_app_compose.ui.components.ButtonComponent
import com.example.movie_app_compose.ui.components.OutlinedTextFieldComponent
import com.example.movie_app_compose.ui.theme.ColorPalette
import com.example.movie_app_compose.ui.theme.MovieAppComposeTheme

class Login : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieAppComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = ColorPalette.background) {
                    LoginContent()
                }
            }
        }
    }
}

@Composable
fun LoginContent(modifier: Modifier = Modifier) {
    ConstraintLayout(
        modifier = modifier.fillMaxHeight()
            .fillMaxWidth()
    ) {
        val (logo, etUsername, etPassword, btnLogin) = createRefs()
        var vUsername by remember { mutableStateOf("") }
        var vPassword by remember { mutableStateOf("") }

        Image(
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = null,
            modifier = Modifier
                .constrainAs(logo) {
                    top.linkTo(parent.top)
                    bottom.linkTo(etUsername.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    centerVerticallyTo(parent)
                }
                .width(150.dp)
                .height(150.dp)
        )

        OutlinedTextFieldComponent(
            value = vUsername,
            name = "Username",
            modifier = modifier.constrainAs(etUsername) {
                top.linkTo(logo.bottom, 64.dp)
                start.linkTo(logo.start)
                end.linkTo(logo.end)
                bottom.linkTo(etPassword.top, 8.dp)
            },
            updateValue = { vUsername = it }
        )

        OutlinedTextFieldComponent(
            value = vPassword,
            name = "Password",
            passwordVisualTransform = true,
            modifier = modifier.constrainAs(etPassword) {
                top.linkTo(etUsername.bottom)
                start.linkTo(etUsername.start)
                end.linkTo(etUsername.end)
            },
            updateValue = { vPassword = it }
        )

        ButtonComponent(
            title = "Login",
            modifier = modifier
                .constrainAs(btnLogin) {
                    top.linkTo(etPassword.bottom)
                    start.linkTo(etPassword.start)
                    end.linkTo(etPassword.end)
                    bottom.linkTo(parent.bottom)
                }
                .width(200.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLogin() {
    MovieAppComposeTheme {
        Surface(color = ColorPalette.background) {
            LoginContent()
        }
    }
}