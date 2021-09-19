package com.example.movie_app_compose

import android.os.Bundle
import android.os.Handler
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.Nullable
import androidx.annotation.StringRes
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
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
import com.example.movie_app_compose.navigation.Navigation
import com.example.movie_app_compose.navigation.ParentNavigation
import com.example.movie_app_compose.navigation.Screen
import com.example.movie_app_compose.ui.add.FormAdd
import com.example.movie_app_compose.ui.detail.Detail
import com.example.movie_app_compose.ui.movie.Movie
import com.example.movie_app_compose.ui.overview.OverviewBody
import com.example.movie_app_compose.ui.save.SaveMenu
import com.example.movie_app_compose.ui.splash.SplashScreenContent
import com.example.movie_app_compose.ui.theme.MovieAppComposeTheme
import com.example.movie_app_compose.ui.tv.Tv
import com.example.movie_app_compose.util.Const
import com.facebook.stetho.Stetho

val items = listOf(
    Screen.Overview,
    Screen.Movie,
    Screen.Add,
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
                    ParentNavigation()
                }
            }

            Stetho.initializeWithDefaults(this);
        }
    }
}

@Composable
fun MainActivityContent(navControllerMainUI: NavController) {
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
                OverviewBody(
                    scrollState = scrollState,
                    onItemClickListener = { type, id ->
                        navigateToDetail(navControllerMainUI, type, id)
                    })
            }
            composable(Screen.Movie.route) {
                scrollState = rememberScrollState()
                Movie(
                    scrollState = scrollState,
                    onItemClickListener = { type, id ->
                        navigateToDetail(navControllerMainUI, type, id)
                    }
                )
            }
            composable(Screen.Add.route){
                scrollState = rememberScrollState()
                FormAdd()
            }
            composable(Screen.Tv.route) {
                scrollState = rememberScrollState()
                Tv(
                    scrollState = scrollState,
                    onItemClickListener = { type, id ->
                        navigateToDetail(navControllerMainUI, type, id)
                    }
                )
            }
            composable(Screen.Save.route) {
                SaveMenu(navController = navControllerMainUI)
            }
        }
    }
}

fun navigateToDetail(navControl: NavController, type: String, id: String, repo: String = Const.TYPE_REPO_REMOTE) {
    navControl.navigate("${Navigation.Detail.router}/$type/$id/$repo")
}

@Preview
@Composable
fun PreviewMainUI() {
    MovieAppComposeTheme {
        Surface {
            ParentNavigation()
        }
    }
}