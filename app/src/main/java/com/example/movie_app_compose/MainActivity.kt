package com.example.movie_app_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.movie_app_compose.ui.movie.Movie
import com.example.movie_app_compose.ui.overview.OverviewBody
import com.example.movie_app_compose.ui.theme.MovieAppComposeTheme
import com.example.movie_app_compose.ui.tv.Tv


//API = 84d705c351638de4d76dad39089aa221

sealed class Screen(val route: String, @StringRes val resId: Int, @DrawableRes val iconId: Int) {
    object Overview : Screen("overview", R.string.overview, R.drawable.ic_baseline_home_24)
    object Movie : Screen("movie", R.string.movie, R.drawable.ic_baseline_movie_24)

    //    object Search : Screen("search", R.string.search, R.drawable.ic_baseline_search_24)
    object Tv : Screen("tv", R.string.tv, R.drawable.ic_baseline_tv_24)
    object Account : Screen("account", R.string.account, R.drawable.ic_baseline_person_24)
}

val items = listOf(
    Screen.Overview,
    Screen.Movie,
//    Screen.Search,
    Screen.Tv,
    Screen.Account
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
                composable(Screen.Account.route) {
                    Text(text = "Text 5")
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