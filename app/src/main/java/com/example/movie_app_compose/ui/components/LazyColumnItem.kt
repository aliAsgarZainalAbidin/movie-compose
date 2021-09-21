package com.example.movie_app_compose.ui.components

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberImagePainter
import com.example.movie_app_compose.BuildConfig
import com.example.movie_app_compose.R
import com.example.movie_app_compose.ui.theme.MovieAppComposeTheme
import com.example.movie_app_compose.util.Const
import java.io.File

@Composable
fun LazyColumnItem(
    modifier: Modifier = Modifier,
    imageUrl: String = "",
    title: String = "",
    date: String = "",
    overview: String = "",
    typeRepo : String = Const.TYPE_REPO_REMOTE
) {
    val fullUrlImage = "${BuildConfig.BASE_IMAGE_URL}$imageUrl"
    var imageUri by remember { mutableStateOf(Uri.fromFile(File(imageUrl))) }
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }
    val data = rememberImagePainter(
        data = fullUrlImage,
        builder = {
            error(R.color.darkblue)
            placeholder(R.color.darkblue)
            crossfade(true)
        })

    Surface(modifier = Modifier.padding(bottom = 16.dp)) {
        Card(modifier = modifier, shape = RoundedCornerShape(8.dp)) {
            ConstraintLayout(
                modifier = modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                val (poster, tvTitle, tvDesc, tvRilis) = createRefs()
                Surface(
                    modifier = modifier.constrainAs(poster) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                    },
                    shape = RoundedCornerShape(8.dp),
                    elevation = 4.dp
                ) {
                    when(typeRepo){
                        Const.TYPE_REPO_REMOTE -> {
                            Image(
                                painter = data,
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = modifier
                                    .height(123.dp)
                                    .width(80.dp)
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
                                        .height(123.dp)
                                        .width(80.dp)
                                )
                            }
                        }
                    }
                }

                TextComponent(
                    value = title,
                    modifier = modifier.constrainAs(tvTitle) {
                        top.linkTo(poster.top)
                        start.linkTo(poster.end, margin = 16.dp)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    },
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.subtitle1,
                    textAlign = TextAlign.Start
                )

                Text(
                    text = overview,
                    modifier = modifier.constrainAs(tvDesc) {
                        top.linkTo(tvRilis.bottom, 16.dp)
                        start.linkTo(tvRilis.start)
                        end.linkTo(parent.end)
                        width = Dimension.preferredWrapContent
                    },
                    style = MaterialTheme.typography.caption,
                    textAlign = TextAlign.Justify,
                    maxLines = 3
                )

                TextComponent(
                    value = date,
                    modifier = modifier.constrainAs(tvRilis) {
                        top.linkTo(tvTitle.bottom, 4.dp)
                        start.linkTo(tvTitle.start)
                        width = Dimension.preferredWrapContent
                    },
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.caption,
                    textAlign = TextAlign.Start
                )

            }
        }
    }
}

@Preview
@Composable
fun PreviewLazyColumnItem() {
    MovieAppComposeTheme {
        LazyColumnItem()
    }
}