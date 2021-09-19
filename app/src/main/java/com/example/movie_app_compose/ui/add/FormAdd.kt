package com.example.movie_app_compose.ui.add

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.example.movie_app_compose.BuildConfig.TAG
import com.example.movie_app_compose.R
import com.example.movie_app_compose.api.ApiFactory
import com.example.movie_app_compose.data.AppDatabase
import com.example.movie_app_compose.data.Repository
import com.example.movie_app_compose.data.entity.MyMovie
import com.example.movie_app_compose.data.entity.MyTvShow
import com.example.movie_app_compose.data.entity.Trending
import com.example.movie_app_compose.data.entity.TrendingLocal
import com.example.movie_app_compose.model.Genre
import com.example.movie_app_compose.navigation.ParentNavigation
import com.example.movie_app_compose.ui.components.OutlinedTextFieldCustom
import com.example.movie_app_compose.ui.components.TextComponent
import com.example.movie_app_compose.ui.detail.DetailViewModel
import com.example.movie_app_compose.ui.theme.DarkBlue900
import com.example.movie_app_compose.ui.theme.MovieAppComposeTheme
import com.example.movie_app_compose.ui.theme.Shapes
import com.example.movie_app_compose.ui.theme.trans
import com.example.movie_app_compose.util.Const
import com.facebook.stetho.Stetho
import com.google.android.material.datepicker.MaterialDatePicker
import java.io.File
import java.io.IOException
import java.security.acl.Permission
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.sin

@Composable
fun FormAdd(
    modifier: Modifier = Modifier
) {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }
    val launcher  = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()){
        imageUri = it
    }
    val restApi by lazy { ApiFactory.create() }
    val formAddViewModel: FormAddViewModel = viewModel()
    formAddViewModel.repositor = Repository(restApi, AppDatabase.getDatabase(LocalContext.current))

    var currentPhotoPath = ""

    imageUri.let {
        if (Build.VERSION.SDK_INT < 28) {
            bitmap.value = it?.let { image ->
                MediaStore.Images
                    .Media.getBitmap(LocalContext.current.contentResolver,image)
            }

        } else {
            val source = it?.let { image ->
                ImageDecoder
                    .createSource(LocalContext.current.contentResolver, image)
            }
            bitmap.value = source?.let { deco -> ImageDecoder.decodeBitmap(deco) }
        }
        currentPhotoPath = it?.path.toString()
    }

    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(colorResource(id = R.color.darkblue), Shapes.large)
                .padding(16.dp)
        ) {
            val (ivSurface, tilTitle, til) = createRefs()
            Surface(
                modifier = modifier
                    .clickable { launcher.launch("image/") }
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
                    val (ivBackdrop) = createRefs()
                    bitmap.value.let { it?.asImageBitmap() }?.let {
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
            val (titleTf, dateTf, popularityTf, adultTf, titleAdult,titleLang, languageTf, titleGenre, genreTf, titleOverview, overviewTf, titleType, typeSpinner, btnSave) = createRefs()

            val titleTfState = remember { mutableStateOf(TextFieldValue()) }
            OutlinedTextField(
                value = titleTfState.value,
                onValueChange = {
                    titleTfState.value = it
                },
                modifier = modifier.constrainAs(titleTf) {
                    top.linkTo(ivSurface.bottom, 8.dp)
                    start.linkTo(ivSurface.start)
                    end.linkTo(ivSurface.end)
                    width = Dimension.fillToConstraints
                },
                singleLine = true,
                placeholder = {
                    TextComponent(value = "Title")
                },
                label = { TextComponent(value = "Title") },
                colors = TextFieldDefaults.textFieldColors(textColor = Color.White)
            )

            val dateTfState = remember { mutableStateOf(TextFieldValue()) }
            OutlinedTextField(
                value = dateTfState.value,
                onValueChange = {
                    dateTfState.value = it
                },
                modifier = modifier.constrainAs(dateTf) {
                    top.linkTo(titleTf.bottom, 8.dp)
                    start.linkTo(titleTf.start)
                    end.linkTo(titleTf.end)
                    width = Dimension.fillToConstraints
                },
                singleLine = true,
                placeholder = {
                    TextComponent(value = "2021-09-13")
                },
                label = { TextComponent(value = "Date") }
            )

            var textFieldSize by remember { mutableStateOf(Size.Zero) }
            val popularityTfState = remember { mutableStateOf(TextFieldValue()) }
            OutlinedTextField(
                value = popularityTfState.value,
                onValueChange = {
                    popularityTfState.value = it
                },
                modifier = modifier
                    .constrainAs(popularityTf) {
                        top.linkTo(dateTf.bottom, 8.dp)
                        start.linkTo(dateTf.start)
                        end.linkTo(dateTf.end)
                        width = Dimension.fillToConstraints
                    }
                    .onGloballyPositioned { textFieldSize = it.size.toSize() },
                singleLine = true,
                placeholder = {
                    TextComponent(value = "589")
                },
                label = { TextComponent(value = "Popularity") }
            )

            var adultDropDownState by remember { mutableStateOf(false) }
            var buttonSize by remember { mutableStateOf(Size.Zero) }
            val adultState = remember { mutableStateOf(TextFieldValue("Pilih Status")) }
            TextComponent(value = "Adult", modifier = modifier.constrainAs(titleAdult){
                top.linkTo(popularityTf.bottom,16.dp)
                start.linkTo(popularityTf.start)
            })
            Column(
                modifier = Modifier
                    .constrainAs(adultTf) {
                        top.linkTo(titleAdult.bottom, 8.dp)
                        start.linkTo(popularityTf.start)
                        end.linkTo(popularityTf.end)
                        width = Dimension.fillToConstraints
                    }
            ) {
                Button(
                    onClick = { adultDropDownState = !adultDropDownState },
                    modifier = modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .onGloballyPositioned { buttonSize = it.size.toSize() }
                ) {
                    TextComponent(value = adultState.value.text)
                }

                DropdownMenu(
                    expanded = adultDropDownState,
                    onDismissRequest = { adultDropDownState = false },
                    modifier = modifier.width(with(LocalDensity.current) {
                        buttonSize.width.toDp()
                    })
                ) {
                    DropdownMenuItem(onClick = {
                        adultState.value = TextFieldValue("Yes")
                        adultDropDownState = false
                    }) {
                        Text("Yes")
                    }
                    DropdownMenuItem(onClick = {
                        adultState.value = TextFieldValue("No")
                        adultDropDownState = false
                    }) {
                        Text("No")
                    }
                }
            }

            var langDropDownState by remember { mutableStateOf(false) }
            val langState = remember { mutableStateOf(TextFieldValue("Pilih Bahasa")) }
            TextComponent(value = "Language", modifier = modifier.constrainAs(titleLang){
                top.linkTo(adultTf.bottom,16.dp)
                start.linkTo(adultTf.start)
            })
            Column(
                modifier = Modifier
                    .constrainAs(languageTf) {
                        top.linkTo(titleLang.bottom, 8.dp)
                        start.linkTo(popularityTf.start)
                        end.linkTo(popularityTf.end)
                        width = Dimension.fillToConstraints
                    }
            ) {
                Button(
                    onClick = { langDropDownState = !langDropDownState },
                    modifier = modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .onGloballyPositioned { buttonSize = it.size.toSize() }
                ) {
                    TextComponent(value = langState.value.text)
                }

                DropdownMenu(
                    expanded = langDropDownState,
                    onDismissRequest = { langDropDownState = false },
                    modifier = modifier.width(with(LocalDensity.current) {
                        buttonSize.width.toDp()
                    })
                ) {
                    DropdownMenuItem(onClick = {
                        langState.value = TextFieldValue("Yes")
                        langDropDownState = false
                    }) {
                        Text("Yes")
                    }
                    //init lang
                }
            }

            val genreTfState = remember { mutableStateOf(TextFieldValue()) }
            OutlinedTextField(
                value = genreTfState.value,
                onValueChange = {
                    genreTfState.value = it
                },
                modifier = modifier
                    .constrainAs(genreTf) {
                        top.linkTo(languageTf.bottom, 8.dp)
                        start.linkTo(dateTf.start)
                        end.linkTo(dateTf.end)
                        width = Dimension.fillToConstraints
                    }
                    .onGloballyPositioned { textFieldSize = it.size.toSize() },
                singleLine = true,
                placeholder = {
                    TextComponent(value = "Action, Romance, etc")
                },
                label = { TextComponent(value = "Genre") }
            )

            val overviewTfState = remember { mutableStateOf(TextFieldValue()) }
            OutlinedTextField(
                value = overviewTfState.value,
                onValueChange = {
                    overviewTfState.value = it
                },
                modifier = modifier
                    .constrainAs(overviewTf) {
                        top.linkTo(genreTf.bottom, 8.dp)
                        start.linkTo(dateTf.start)
                        end.linkTo(dateTf.end)
                        width = Dimension.fillToConstraints
                    }
                    .onGloballyPositioned { textFieldSize = it.size.toSize() },
                singleLine = true,
                placeholder = {
                    TextComponent(value = "Once open time, there is ...")
                },
                label = { TextComponent(value = "Overview") },
            )

            var typeDropDownState by remember { mutableStateOf(false) }
            val typeState = remember { mutableStateOf(TextFieldValue("Pilih Type Item")) }
            TextComponent(value = "Type Item", modifier = modifier.constrainAs(titleType){
                top.linkTo(overviewTf.bottom,16.dp)
                start.linkTo(adultTf.start)
            })
            Column(
                modifier = Modifier
                    .constrainAs(typeSpinner) {
                        top.linkTo(titleType.bottom, 8.dp)
                        start.linkTo(popularityTf.start)
                        end.linkTo(popularityTf.end)
                        width = Dimension.fillToConstraints
                    }
            ) {
                Button(
                    onClick = { typeDropDownState = !typeDropDownState },
                    modifier = modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .onGloballyPositioned { buttonSize = it.size.toSize() }
                ) {
                    TextComponent(value = typeState.value.text)
                }

                DropdownMenu(
                    expanded = typeDropDownState,
                    onDismissRequest = { typeDropDownState = false },
                    modifier = modifier.width(with(LocalDensity.current) {
                        buttonSize.width.toDp()
                    })
                ) {
                    DropdownMenuItem(onClick = {
                        typeState.value = TextFieldValue("Movie")
                        typeDropDownState = false
                    }) {
                        Text("Movie")
                    }
                    DropdownMenuItem(onClick = {
                        typeState.value = TextFieldValue("Tv Show")
                        typeDropDownState = false
                    }) {
                        Text("Tv Show")
                    }

                }
            }

            Button(
                onClick = {
                          //Logic save item
                          if (titleTfState.value.text.isNotEmpty() && dateTfState.value.text.isNotEmpty() && popularityTfState.value.text.isNotEmpty() && adultState.value.text.isNotEmpty()  && !adultState.value.text.equals("Pilih Status") && langState.value.text.isNotEmpty() && !langState.value.text.equals("Pilih Bahasa") && genreTfState.value.text.isNotEmpty() && overviewTfState.value.text.isNotEmpty() && typeState.value.text.isNotEmpty() && !typeState.value.text.equals("Pilih Type Item") ){
                              if (typeState.value.text.equals("Movie")) {
                                  val trending = TrendingLocal()
                                  trending.title = titleTfState.value.text
                                  trending.releaseDate = dateTfState.value.text
                                  trending.popularity = popularityTfState.value.text.toDouble()
                                  trending.adult = adultState.value.text.equals("Yes")
                                  trending.originalLanguage = langState.value.text
                                  var listGenre = ArrayList<Genre>()
                                  genreTfState.value.text.split(",").forEach {
                                      val genre = Genre()
                                      genre.name = it
                                      listGenre.add(genre)
                                  }
                                  trending.backdropPath = currentPhotoPath
                                  trending.posterPath = currentPhotoPath
                                  trending.mediaType = "movie"
                                  trending.genres = listGenre
                                  trending.overview = overviewTfState.value.text
                                  formAddViewModel.insertTrendingMovie(trending)
                              }
                          }else {
                              Log.d(TAG, "FormAdd: false")
                          }
                },
                modifier = modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .constrainAs(btnSave) {
                        top.linkTo(typeSpinner.bottom, 32.dp)
                        start.linkTo(typeSpinner.start)
                        end.linkTo(typeSpinner.end)
                        width = Dimension.fillToConstraints
                    }
            ) {
                TextComponent(value = "Simpan")
            }

        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewFormAdd() {
    MovieAppComposeTheme {
        FormAdd()
    }
}