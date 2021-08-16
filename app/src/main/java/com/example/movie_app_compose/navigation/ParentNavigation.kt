package com.example.movie_app_compose.navigation

import android.os.Handler
import androidx.compose.runtime.*
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.example.movie_app_compose.MainActivityContent
import com.example.movie_app_compose.ui.detail.Detail
import com.example.movie_app_compose.ui.login.LoginContent
import com.example.movie_app_compose.ui.splash.SplashScreenContent

@Composable
fun ParentNavigation() {
    val navControllerMainUI = rememberNavController()
    var splashScreenActive by remember { mutableStateOf(false) }
    NavHost(
        navController = navControllerMainUI,
        startDestination = Navigation.SplashScreen.router
    ) {
        composable(Navigation.SplashScreen.router) {
            SplashScreenContent()
            Handler().postDelayed({
                navControllerMainUI.navigate(Navigation.Activity.router) {
//                        splashScreenActive = true
                    popUpTo(Navigation.SplashScreen.router){
                        inclusive = true
                    }
                    launchSingleTop = true
                    restoreState = false
                }
            }, 1500)
        }
        composable(Navigation.Detail.router) {
            Detail()
        }
        composable(Navigation.Activity.router) {
            MainActivityContent(navControllerMainUI)
        }
        composable(
            Navigation.Login.router
        ) {
            LoginContent(onClickButtonLogin = {
                navControllerMainUI.popBackStack(Navigation.SplashScreen.router, true, false)
                navControllerMainUI.navigate(Navigation.Activity.router)
            })
        }
    }
}