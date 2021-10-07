package com.example.movie_app_compose.ui.add

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
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
import android.widget.DatePicker
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
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
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
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
import com.example.movie_app_compose.MainActivity
import com.example.movie_app_compose.R
import com.example.movie_app_compose.api.ApiFactory
import com.example.movie_app_compose.data.AppDatabase
import com.example.movie_app_compose.data.Repository
import com.example.movie_app_compose.data.entity.*
import com.example.movie_app_compose.model.Genre
import com.example.movie_app_compose.navigation.ParentNavigation
import com.example.movie_app_compose.ui.components.OutlinedTextFieldCustom
import com.example.movie_app_compose.ui.components.SimpleCheckBox
import com.example.movie_app_compose.ui.components.TextComponent
import com.example.movie_app_compose.ui.detail.DetailViewModel
import com.example.movie_app_compose.ui.theme.*
import com.example.movie_app_compose.util.Const
import com.facebook.stetho.Stetho
import com.google.android.material.datepicker.MaterialDatePicker
import java.io.File
import java.io.IOException
import java.security.acl.Permission
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.sin

@Composable
fun FormAdd(
    modifier: Modifier = Modifier
) {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {
            imageUri = it
        }
    val restApi by lazy { ApiFactory.create() }
    val formAddViewModel: FormAddViewModel = viewModel()
    formAddViewModel.repositor = Repository(restApi, AppDatabase.getDatabase(LocalContext.current))
    var buttonSize by remember { mutableStateOf(Size.Zero) }

    var currentPhotoPath = ""

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
        var cursor =
            it?.let { uri ->
                LocalContext.current.contentResolver.query(
                    uri,
                    null,
                    null,
                    null,
                    null
                )
            }
        if (cursor == null) {
            currentPhotoPath = it?.path.toString()
        } else {
            cursor.moveToFirst()
            val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            currentPhotoPath = cursor.getString(idx)
            cursor.close()
        }
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
            val (titleTf, dateTf, popularityTf, adultTf, titleAdult, titleDate, languageTf, titleGenre, genreTf, titleOverview, overviewTf, titleType, typeSpinner, btnSave) = createRefs()

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

            val mYear: Int
            val mMonth: Int
            val mDay: Int
            val now = Calendar.getInstance()
            mYear = now.get(Calendar.YEAR)
            mMonth = now.get(Calendar.MONTH)
            mDay = now.get(Calendar.DAY_OF_MONTH)
            now.time = Date()
            val date = remember { mutableStateOf("") }
            val datePickerDialog = DatePickerDialog(
                LocalContext.current,
                { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                    val cal = Calendar.getInstance()
                    cal.set(year, month, dayOfMonth)
                    date.value = SimpleDateFormat("yyyy-MM-dd").format(cal.time)
                }, mYear, mMonth, mDay
            )

            TextComponent(value = "Date", modifier = modifier.constrainAs(titleDate) {
                top.linkTo(titleTf.bottom, 16.dp)
                start.linkTo(titleTf.start)
            })
            Button(
                onClick = { datePickerDialog.show() },
                modifier = modifier
                    .constrainAs(dateTf) {
                        top.linkTo(titleDate.bottom, 8.dp)
                        start.linkTo(titleTf.start)
                        end.linkTo(titleTf.end)
                        width = Dimension.fillToConstraints
                    }
                    .fillMaxWidth()
                    .height(48.dp)
                    .onGloballyPositioned { buttonSize = it.size.toSize() },
                colors = ButtonDefaults.buttonColors(backgroundColor = Green800)
            ) {
                TextComponent(value = date.value)
            }

            var textFieldSize by remember { mutableStateOf(Size.Zero) }
            val popularityTfState = remember { mutableStateOf(TextFieldValue("0")) }
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
                label = { TextComponent(value = "Popularity") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            var adultDropDownState by remember { mutableStateOf(false) }
            val adultState = remember { mutableStateOf(TextFieldValue("Pilih Status")) }
            TextComponent(value = "Adult", modifier = modifier.constrainAs(titleAdult) {
                top.linkTo(popularityTf.bottom, 16.dp)
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
                        .onGloballyPositioned { buttonSize = it.size.toSize() },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Green800)
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

            val langState = remember { mutableStateOf(TextFieldValue()) }
            OutlinedTextField(
                value = langState.value,
                onValueChange = {
                    langState.value = it
                },
                modifier = Modifier
                    .constrainAs(languageTf) {
                        top.linkTo(adultTf.bottom, 8.dp)
                        start.linkTo(popularityTf.start)
                        end.linkTo(popularityTf.end)
                        width = Dimension.fillToConstraints
                    }
                    .onGloballyPositioned { textFieldSize = it.size.toSize() },
                singleLine = true,
                placeholder = {
                    TextComponent(value = "English, Indonesian, etc")
                },
                label = { TextComponent(value = "Language") },
            )

//            val genreTfState = remember { mutableStateOf(TextFieldValue()) }
//            OutlinedTextField(
//                value = genreTfState.value,
//                onValueChange = {
//                    genreTfState.value = it
//                },
//                modifier = modifier
//                    .constrainAs(genreTf) {
//                        top.linkTo(languageTf.bottom, 8.dp)
//                        start.linkTo(dateTf.start)
//                        end.linkTo(dateTf.end)
//                        width = Dimension.fillToConstraints
//                    }
//                    .onGloballyPositioned { textFieldSize = it.size.toSize() },
//                singleLine = true,
//                placeholder = {
//                    TextComponent(value = "Action, Romance, etc")
//                },
//                label = { TextComponent(value = "Genre") }
//            )


            val romanceGenre = remember { mutableStateOf("Romance") }
            val actionGenre = remember { mutableStateOf("Action") }
            val horrorGenre = remember { mutableStateOf("Horror") }
            val romanceState = remember { mutableStateOf(false) }
            val actionState = remember { mutableStateOf(false) }
            val horrorState = remember { mutableStateOf(false) }

            Row(modifier = modifier
                .padding(vertical = 16.dp)
                .constrainAs(genreTf) {
                    top.linkTo(languageTf.bottom, 8.dp)
                    start.linkTo(dateTf.start)
                    end.linkTo(dateTf.end)
                    width = Dimension.fillToConstraints
                }) {

                Row(modifier = modifier) {
                    Checkbox(
                        checked = romanceState.value,
                        modifier = Modifier.padding(vertical = 16.dp),
                        onCheckedChange = { romanceState.value = it },
                    )
                    Text(text = romanceGenre.value, modifier = Modifier.padding(4.dp,16.dp,16.dp,4.dp))
                }

                Row(modifier = modifier) {
                    Checkbox(
                        checked = actionState.value,
                        modifier = Modifier.padding(vertical = 16.dp,),
                        onCheckedChange = { actionState.value = it },
                    )
                    Text(text = actionGenre.value, modifier = Modifier.padding(4.dp,16.dp,16.dp,4.dp))
                }

                Row(modifier = modifier) {
                    Checkbox(
                        checked = horrorState.value,
                        modifier = Modifier.padding(vertical = 16.dp,),
                        onCheckedChange = { horrorState.value = it },
                    )
                    Text(text = horrorGenre.value, modifier = Modifier.padding(4.dp,16.dp,16.dp,4.dp))
                }
            }

            val overviewTfState = remember { mutableStateOf(TextFieldValue()) }
            OutlinedTextField(
                value = overviewTfState.value,
                onValueChange = {
                    overviewTfState.value = it
                },
                modifier = modifier
                    .constrainAs(overviewTf) {
                        top.linkTo(genreTf.bottom)
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
            TextComponent(value = "Type Item", modifier = modifier.constrainAs(titleType) {
                top.linkTo(overviewTf.bottom, 16.dp)
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
                        .onGloballyPositioned { buttonSize = it.size.toSize() },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Green800)
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
                    if (titleTfState.value.text.isNotEmpty() && date.value.isNotEmpty() && popularityTfState.value.text.isNotEmpty() && adultState.value.text.isNotEmpty() && !adultState.value.text.equals(
                            "Pilih Status"
                        ) && langState.value.text.isNotEmpty()  && overviewTfState.value.text.isNotEmpty() && typeState.value.text.isNotEmpty() && !typeState.value.text.equals(
                            "Pilih Type Item"
                        )
                    ) {
                        if (typeState.value.text.equals("Movie")) {
                            val trending = TrendingLocal()
                            trending.title = titleTfState.value.text
                            trending.releaseDate = date.value
                            trending.popularity = popularityTfState.value.text.toDouble()
                            trending.adult = adultState.value.text.equals("Yes")
                            trending.originalLanguage = langState.value.text
                            var listGenre = ArrayList<Genre>()
                            if (romanceState.value){
                                val genre = Genre()
                                genre.name = romanceGenre.value
                                listGenre.add(genre)
                            }
                            if (actionState.value){
                                val genre = Genre()
                                genre.name = actionGenre.value
                                listGenre.add(genre)
                            }
                            if (horrorState.value){
                                val genre = Genre()
                                genre.name = horrorGenre.value
                                listGenre.add(genre)
                            }

                            trending.backdropPath = currentPhotoPath
                            trending.posterPath = currentPhotoPath
                            trending.mediaType = "movie"
                            trending.genres = listGenre
                            trending.overview = overviewTfState.value.text
                            trending.typeTrending = Const.TYPE_TRENDING_LOCAL
                            formAddViewModel.insertTrendingMovie(trending)
                        } else if (typeState.value.text.equals("Tv Show")) {
                            var listGenre = ArrayList<Genre>()
                            if (romanceState.value){
                                val genre = Genre()
                                genre.name = romanceGenre.value
                                listGenre.add(genre)
                            }
                            if (actionState.value){
                                val genre = Genre()
                                genre.name = actionGenre.value
                                listGenre.add(genre)
                            }
                            if (horrorState.value){
                                val genre = Genre()
                                genre.name = horrorGenre.value
                                listGenre.add(genre)
                            }

                            val onTheAirLocal = OnTheAirLocal(
                                voteAverage = 0f,
                                backdropPath = currentPhotoPath,
                                firstAirDate = date.value,
                                genres = listGenre,
                                language = langState.value.text,
                                overview = overviewTfState.value.text,
                                popularity = popularityTfState.value.text.toDouble(),
                                posterPath = currentPhotoPath,
                                name = titleTfState.value.text,
                                id = null,
                                typeOnTheAir = Const.TYPE_ONTHEAIR_LOCAL,
                                adult = adultState.value.text.equals("Yes")
                            )
                            formAddViewModel.insertOnTheAir(onTheAirLocal)
                        }
                        titleTfState.value = TextFieldValue("")
                        date.value = ""
                        popularityTfState.value = TextFieldValue("0")
                        adultState.value = TextFieldValue("Pilih Status")
                        langState.value = TextFieldValue("")
                        overviewTfState.value = TextFieldValue("")
                        typeState.value = TextFieldValue("Pilih Type Item")

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