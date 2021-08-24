package com.example.movie_app_compose.ui.detail

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.example.movie_app_compose.BuildConfig
import com.example.movie_app_compose.BuildConfig.TAG
import com.example.movie_app_compose.R
import com.example.movie_app_compose.api.ApiFactory
import com.example.movie_app_compose.data.AppDatabase
import com.example.movie_app_compose.data.Repository
import com.example.movie_app_compose.model.Genre
import com.example.movie_app_compose.ui.IdleContent
import com.example.movie_app_compose.ui.components.Chip
import com.example.movie_app_compose.ui.components.TextComponent
import com.example.movie_app_compose.ui.offline.OfflineContent
import com.example.movie_app_compose.ui.theme.DarkBlue900
import com.example.movie_app_compose.ui.theme.Grey
import com.example.movie_app_compose.ui.theme.MovieAppComposeTheme
import java.util.*

@Composable
fun Detail(
    modifier: Modifier = Modifier,
    id: String = "",
    type: String = "",
    title: String = "",
    imageUrl: String = "",
    titleDate: String = "",
    date: String = "",
    popularity: String = "",
    adult: String = "",
    language: String = "",
    overview: String = "",
    listGenre: List<Genre> = listOf(),
) {
    Log.d(TAG, "Detail: $title")
    if (title != "null") {
        DetailContent(
            id = id,
            type = type,
            title = title,
            imageUrl = imageUrl,
            titleDate = titleDate,
            date = date,
            adult = adult,
            overview = overview,
            language = language,
            popularity = popularity,
            listGenre = listGenre
        )
    } else {
        val connectionManager =
            LocalContext.current.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectionManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)?.state == NetworkInfo.State.CONNECTED ||
            connectionManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)?.state == NetworkInfo.State.CONNECTED
        ) {
            IdleContent()
        } else {
            OfflineContent()
        }
    }
}

@Preview
@Composable
fun PreviewDetail() {
    MovieAppComposeTheme {
        Surface(color = DarkBlue900) {
            DetailContent()
        }
    }
}