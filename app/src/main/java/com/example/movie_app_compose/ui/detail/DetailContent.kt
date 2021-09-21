package com.example.movie_app_compose.ui.detail

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.airbnb.lottie.compose.*
import com.example.movie_app_compose.BuildConfig
import com.example.movie_app_compose.BuildConfig.TAG
import com.example.movie_app_compose.R
import com.example.movie_app_compose.api.ApiFactory
import com.example.movie_app_compose.data.AppDatabase
import com.example.movie_app_compose.data.Repository
import com.example.movie_app_compose.data.entity.MyMovie
import com.example.movie_app_compose.data.entity.MyTvShow
import com.example.movie_app_compose.model.Detail
import com.example.movie_app_compose.model.Genre
import com.example.movie_app_compose.ui.components.ButtonComponent
import com.example.movie_app_compose.ui.components.Chip
import com.example.movie_app_compose.ui.components.TextComponent
import com.example.movie_app_compose.ui.theme.DarkBlue900
import com.example.movie_app_compose.ui.theme.Green500
import com.example.movie_app_compose.ui.theme.Grey
import com.example.movie_app_compose.ui.theme.MovieAppComposeTheme
import com.example.movie_app_compose.util.Const
import com.example.movie_app_compose.util.Mapper
import java.io.File
import java.util.*

@Composable
fun DetailContent(
    modifier: Modifier = Modifier,
    id: String = "",
    type: String = "",
    posterPath: String = "",
    title: String = "",
    imageUrl: String = "",
    titleDate: String = "",
    date: String = "",
    popularity: String = "",
    adult: String = "-",
    language: String = "",
    overview: String = "",
    listGenre: List<Genre> = listOf(),
    isSaved: Boolean = false,
    navController: NavController = rememberNavController(),
    typeRepo: String = Const.TYPE_REPO_REMOTE
) {
    val restApi by lazy { ApiFactory.create() }
    var progress by remember {
        mutableStateOf(if (isSaved) 1f else 0f)
    }
    val detailViewModel: DetailViewModel = viewModel()
    detailViewModel.repository = Repository(restApi, AppDatabase.getDatabase(LocalContext.current))

    var imageUri by remember { mutableStateOf(Uri.fromFile(File(imageUrl))) }
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }

    val animationSpec by
    rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.lf30_editor_24))

    Log.d(TAG, "DetailContent: $typeRepo")
    val image = rememberImagePainter(
        data = imageUrl,
        builder = {
            error(R.color.darkblue)
            placeholder(R.color.darkblue)
            crossfade(true)
        })

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = DarkBlue900)
    ) {
        ConstraintLayout(
            modifier = modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            val (tvTitle, ivSurface, lzCategory, tvCategory) = createRefs()
            val (tvTitleOver, tvOverview, tvTitleDate, tvDate, tvTitleAdult, tvAdult, tvTitlePopularity, tvPopularity, tvTitleLanguage, tvLanguage, btnDelete) = createRefs()
            val backImage = painterResource(id = R.drawable.landscape)

            Surface(
                modifier = modifier
                    .height(272.dp)
                    .constrainAs(ivSurface) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                        height = Dimension.preferredWrapContent
                    },
                shape = RoundedCornerShape(16.dp)
            ) {
                ConstraintLayout {
                    val (ivBackdrop, view, lottie, surface, ivBack) = createRefs()

                    when(typeRepo){
                        Const.TYPE_REPO_REMOTE -> {
                            Image(
                                painter = image,
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight()
                                    .constrainAs(ivBackdrop) {
                                        top.linkTo(parent.top)
                                        bottom.linkTo(parent.bottom)
                                    },
                                alignment = Alignment.Center
                            )
                        }
                        Const.TYPE_TRENDING_LOCAL, Const.TYPE_ONTHEAIR_LOCAL -> {
                            imageUri.let {
                                if (Build.VERSION.SDK_INT < 28) {
                                    bitmap.value = it?.let { image ->
                                        MediaStore.Images
                                            .Media.getBitmap(LocalContext.current.contentResolver, image)
                                    }

                                } else {
                                    val source = it?.let { image ->
                                        ImageDecoder
                                            .createSource(LocalContext.current.contentResolver, image)
                                    }
                                    bitmap.value = source?.let { deco -> ImageDecoder.decodeBitmap(deco) }
                                }
                            }
                            bitmap.value?.asImageBitmap()?.let {
                                Image(
                                    bitmap = it,
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = modifier
                                        .fillMaxWidth()
                                        .fillMaxHeight()
                                        .constrainAs(ivBackdrop) {
                                            top.linkTo(parent.top)
                                            bottom.linkTo(parent.bottom)
                                        },
                                    alignment = Alignment.Center
                                )
                            }
                        }
                    }

                    Image(
                        painter = painterResource(id = R.drawable.ic_baseline_arrow_back_ios_24),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = modifier
                            .clickable {
                                navController.popBackStack()
                            }
                            .padding(8.dp)
                            .size(24.dp)
                            .constrainAs(ivBack) {
                                top.linkTo(parent.top, 8.dp)
                                start.linkTo(parent.start, 8.dp)
                            }
                            ,
                        alignment = Alignment.Center
                    )

                    Surface(
                        modifier = modifier
                            .size(32.dp)
                            .alpha(0.5f)
                            .constrainAs(surface) {
                                top.linkTo(parent.top, 8.dp)
                                end.linkTo(parent.end, 8.dp)
                            },
                        shape = CircleShape,
                        color = DarkBlue900, content = {})

                    LottieAnimation(
                        animationSpec,
                        progress = progress,
                        alignment = Alignment.Center,
                        modifier = modifier
                            .constrainAs(lottie) {
                                centerTo(surface)
                            }
                            .size(24.dp)
                            .clickable {
                                when (type) {
                                    Const.TYPE_MOVIE -> {
                                        if (!isSaved) {
                                            detailViewModel.insertToMyMovies(
                                                MyMovie(
                                                    id = id.toInt(),
                                                    type = type,
                                                    title = title,
                                                    backdropPath = imageUrl,
                                                    releaseDate = date,
                                                    popularity = popularity.toDouble(),
                                                    adult = adult.equals("YES"),
                                                    language = language,
                                                    overview = overview,
                                                    genreIds = listGenre,
                                                    posterPath = posterPath,
                                                    isSaved = true,
                                                    typeRepo = typeRepo
                                                )
                                            )
                                            progress = 1f
                                        } else {
                                            progress = 0f
                                            detailViewModel.deleteMovieById(id)
                                        }
                                    }
                                    Const.TYPE_TV -> {
                                        if (!isSaved) {
                                            detailViewModel.insertToMyTvShow(
                                                MyTvShow(
                                                    id = id.toInt(),
                                                    type = type,
                                                    name = title,
                                                    backdropPath = imageUrl,
                                                    firstAirDate = date,
                                                    popularity = popularity.toDouble(),
                                                    language = language,
                                                    overview = overview,
                                                    posterPath = posterPath,
                                                    genres = listGenre,
                                                    isSaved = true,
                                                    typeRepo = typeRepo
                                                )
                                            )
                                            progress = 1f
                                        } else {
                                            progress = 0f
                                            detailViewModel.deleteTvShowById(id)
                                        }
                                    }
                                }
                            }
                    )

                    Spacer(modifier = modifier
                        .alpha(0.5f)
                        .background(DarkBlue900)
                        .height(48.dp)
                        .fillMaxWidth()
                        .constrainAs(view) {
                            bottom.linkTo(parent.bottom)
                        })
                }
            }

            Text(
                text = title,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.h6,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start,
                modifier = modifier
                    .constrainAs(tvTitle) {
                        bottom.linkTo(ivSurface.bottom, 12.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
                    .padding(horizontal = 16.dp)
            )
            TextComponent(
                value = titleDate,
                modifier = modifier.constrainAs(tvTitleDate) {
                    top.linkTo(ivSurface.bottom, 12.dp)
                    start.linkTo(parent.start)
                },
                style = MaterialTheme.typography.caption
            )
            TextComponent(
                value = date,
                modifier = modifier.constrainAs(tvDate) {
                    top.linkTo(tvTitleDate.bottom)
                    start.linkTo(tvTitleDate.start)
                },
                style = MaterialTheme.typography.caption,
                color = Grey
            )
            TextComponent(
                value = "Popularity",
                modifier = modifier.constrainAs(tvTitlePopularity) {
                    top.linkTo(tvDate.bottom, 12.dp)
                    start.linkTo(tvDate.start)
                },
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.caption
            )
            TextComponent(
                value = popularity,
                modifier = modifier.constrainAs(tvPopularity) {
                    top.linkTo(tvTitlePopularity.bottom)
                    start.linkTo(tvTitlePopularity.start)
                },
                style = MaterialTheme.typography.caption,
                color = Grey
            )
            val guideline = createGuidelineFromStart(0.5f)
            TextComponent(
                value = "Adult",
                modifier = modifier.constrainAs(tvTitleAdult) {
                    top.linkTo(tvTitleDate.top)
                    start.linkTo(guideline)
                },
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.caption
            )
            TextComponent(
                value = adult,
                modifier = modifier.constrainAs(tvAdult) {
                    top.linkTo(tvTitleAdult.bottom)
                    start.linkTo(tvTitleAdult.start)
                },
                style = MaterialTheme.typography.caption,
                color = Grey
            )
            TextComponent(
                value = "Language",
                modifier = modifier.constrainAs(tvTitleLanguage) {
                    top.linkTo(tvAdult.bottom, 12.dp)
                    start.linkTo(tvAdult.start)
                },
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.caption
            )
            TextComponent(
                value = language.uppercase(Locale.getDefault()),
                modifier = modifier.constrainAs(tvLanguage) {
                    top.linkTo(tvTitleLanguage.bottom)
                    start.linkTo(tvTitleLanguage.start)
                },
                style = MaterialTheme.typography.caption,
                color = Grey
            )

            TextComponent(
                value = "Genre",
                modifier = modifier.constrainAs(tvCategory) {
                    top.linkTo(tvPopularity.bottom, 12.dp)
                    start.linkTo(tvPopularity.start)
                },
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.caption
            )

            LazyRow(
                modifier = modifier.constrainAs(lzCategory) {
                    top.linkTo(tvCategory.bottom, 4.dp)
                    start.linkTo(tvCategory.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
            ) {
                items(listGenre.size) { index ->
                    Chip(text = listGenre.get(index).name.toString())
                }
            }

            TextComponent(
                value = "Overview",
                modifier = modifier.constrainAs(tvTitleOver) {
                    top.linkTo(lzCategory.bottom, 12.dp)
                    start.linkTo(lzCategory.start)
                },
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.caption,
            )
            Spacer(
                modifier = modifier
                    .height(8.dp)
                    .fillMaxWidth()
            )
            TextComponent(
                value = overview,
                modifier = modifier.constrainAs(tvOverview) {
                    top.linkTo(tvTitleOver.bottom)
                    start.linkTo(tvTitleOver.start)
                },
                style = MaterialTheme.typography.caption,
                maxLines = Int.MAX_VALUE,
                textAlign = TextAlign.Justify
            )
            when(typeRepo){
                Const.TYPE_TRENDING_LOCAL ->{
                    ButtonComponent(
                        title = "Hapus",
                        onButtonClick = {
                            detailViewModel.deletedLocalTrendingById(id)
                            detailViewModel.deleteMovieById(id)
                            navController.popBackStack()
                        },
                        modifier = modifier.constrainAs(btnDelete) {
                            top.linkTo(tvOverview.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }.fillMaxWidth(),
                    )
                }
                Const.TYPE_ONTHEAIR_LOCAL -> {
                    ButtonComponent(
                        title = "Hapus",
                        onButtonClick = {
                            detailViewModel.deletedLocalOnTheAirById(id)
                            detailViewModel.deleteTvShowById(id)
                            navController.popBackStack()
                        },
                        modifier = modifier.constrainAs(btnDelete) {
                            top.linkTo(tvOverview.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }.fillMaxWidth(),
                    )
                }
            }
            Spacer(
                modifier = modifier
                    .height(16.dp)
                    .fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
fun PreviewDetailContent() {
    MovieAppComposeTheme {
        DetailContent(
            title = "Jetpack Compose",
            popularity = "1000",
            language = "EN",
            overview = "Hallo World",
            adult = "YES",
            titleDate = "Release Date",
            imageUrl = "${BuildConfig.BASE_IMAGE_URL}/j28p5VwI5ieZnNwfeuZ5Ve3mPsn.jpg",
            date = "18-08-1999"
        )
    }
}