package com.example.movie_app_compose.ui.add

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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.airbnb.lottie.compose.LottieAnimation
import com.example.movie_app_compose.R
import com.example.movie_app_compose.data.entity.MyMovie
import com.example.movie_app_compose.data.entity.MyTvShow
import com.example.movie_app_compose.ui.components.OutlinedTextFieldCustom
import com.example.movie_app_compose.ui.components.TextComponent
import com.example.movie_app_compose.ui.theme.DarkBlue900
import com.example.movie_app_compose.ui.theme.MovieAppComposeTheme
import com.example.movie_app_compose.ui.theme.Shapes
import com.example.movie_app_compose.ui.theme.trans
import com.example.movie_app_compose.util.Const
import com.google.android.material.datepicker.MaterialDatePicker
import kotlin.math.sin

@Composable
fun FormAdd(
    modifier: Modifier = Modifier
) {
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
                    Image(
                        painter = painterResource(id = R.drawable.ic_baseline_add_24),
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
                label = { TextComponent(value = "Title") }
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
                val adultState = remember { mutableStateOf(TextFieldValue("Pilih Status")) }
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
                val langState = remember { mutableStateOf(TextFieldValue("Pilih Bahasa")) }
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

            TextComponent(value = "Genre", modifier = modifier.constrainAs(titleGenre){
                top.linkTo(languageTf.bottom,16.dp)
                start.linkTo(languageTf.start)
            })
            val genreTfState = remember { mutableStateOf(TextFieldValue()) }
            OutlinedTextField(
                value = genreTfState.value,
                onValueChange = {
                    genreTfState.value = it
                },
                modifier = modifier
                    .constrainAs(genreTf) {
                        top.linkTo(titleGenre.bottom, 8.dp)
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

            TextComponent(value = "Overview", modifier = modifier.constrainAs(titleOverview){
                top.linkTo(genreTf.bottom,16.dp)
                start.linkTo(genreTf.start)
            })
            val overviewTfState = remember { mutableStateOf(TextFieldValue()) }
            OutlinedTextField(
                value = overviewTfState.value,
                onValueChange = {
                    overviewTfState.value = it
                },
                modifier = modifier
                    .constrainAs(overviewTf) {
                        top.linkTo(titleOverview.bottom, 8.dp)
                        start.linkTo(dateTf.start)
                        end.linkTo(dateTf.end)
                        width = Dimension.fillToConstraints
                    }
                    .onGloballyPositioned { textFieldSize = it.size.toSize() },
                singleLine = true,
                placeholder = {
                    TextComponent(value = "Once open time, there is ...")
                },
                label = { TextComponent(value = "Overview") }
            )

            var typeDropDownState by remember { mutableStateOf(false) }
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
                val typeState = remember { mutableStateOf(TextFieldValue("Pilih Type Item")) }
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
                },
                modifier = modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .constrainAs(btnSave){
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