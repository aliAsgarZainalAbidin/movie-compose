package com.example.movie_app_compose

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.Nullable
import androidx.annotation.StringRes
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.movie_app_compose.navigation.MainNavigation
import com.example.movie_app_compose.ui.detail.Detail
import com.example.movie_app_compose.ui.movie.Movie
import com.example.movie_app_compose.ui.overview.OverviewBody
import com.example.movie_app_compose.ui.save.SaveMenu
import com.example.movie_app_compose.ui.splash.SplashScreenContent
import com.example.movie_app_compose.ui.theme.MovieAppComposeTheme
import com.example.movie_app_compose.ui.tv.Tv
import kotlinx.coroutines.delay


//API = 84d705c351638de4d76dad39089aa221

sealed class Screen(
    val route: String,
    @StringRes val resId: Int,
    @DrawableRes @Nullable val iconId: Int
) {
    object Overview : Screen("overview", R.string.overview, R.drawable.ic_baseline_home_24)
    object Movie : Screen("movie", R.string.movie, R.drawable.ic_baseline_movie_24)

    //    object Search : Screen("search", R.string.search, R.drawable.ic_baseline_search_24)
    object Tv : Screen("tv", R.string.tv, R.drawable.ic_baseline_tv_24)
    object Save : Screen("save", R.string.save, R.drawable.ic_baseline_save_alt_24)
}

val items = listOf(
    Screen.Overview,
    Screen.Movie,
//    Screen.Search,
    Screen.Tv,
    Screen.Save
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieAppComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MainUI()
                }
            }
        }
    }

    @Composable
    fun MainUI() {
        val navControllerMainUI = rememberNavController()
        var splashScreenActive by remember { mutableStateOf(false) }
        NavHost(
            navController = navControllerMainUI,
            startDestination = MainNavigation.SplashScreen.router
        ) {
            composable(MainNavigation.SplashScreen.router) {
                SplashScreenContent()
                Handler().postDelayed({
                    navControllerMainUI.navigate(MainNavigation.MainActivity.router) {
//                        splashScreenActive = true
                        launchSingleTop = true
                        restoreState = false
                    }
                }, 1500)
            }
            composable(MainNavigation.Detail.router) {
                Detail()
            }
            composable(MainNavigation.MainActivity.router) {
                MainActivityContent(navControllerMainUI)
            }
        }
    }

    @Composable
    fun MainActivityContent(navControllerMainUI : NavController) {
        val navController = rememberNavController()
        var scrollState: ScrollState

        Scaffold(bottomBar = {
            BottomNavigation {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { screen ->
                    BottomNavigationItem(icon = {
                        Icon(
                            painter = painterResource(id = screen.iconId),
                            contentDescription = null
                        )
                    },
                        label = { Text(stringResource(id = screen.resId)) },
                        selected = currentDestination?.hierarchy?.any() { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = false
                            }
                        })
                }
            }
        }) {
            NavHost(
                navController = navController,
                startDestination = Screen.Overview.route,
                modifier = Modifier.padding(it)
            ) {
                composable(Screen.Overview.route) {
                    scrollState = rememberScrollState()
                    OverviewBody(scrollState = scrollState)
                }
                composable(Screen.Movie.route) {
                    scrollState = rememberScrollState()
                    Movie(scrollState = scrollState)
                }

//                composable(Screen.Search.route) {
//                    Text(text = "Text 3")
//                }
                composable(Screen.Tv.route) {
                    scrollState = rememberScrollState()
                    Tv(scrollState = scrollState)
                }
                composable(Screen.Save.route) {
                    SaveMenu(navController = navControllerMainUI)
                }
            }
        }
    }

    @Preview
    @Composable
    fun PreviewMainUI() {
        MovieAppComposeTheme {
            Surface {
                MainUI()
            }
        }
    }
}