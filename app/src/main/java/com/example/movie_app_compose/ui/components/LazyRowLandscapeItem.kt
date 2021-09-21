package com.example.movie_app_compose.ui.components

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.view.animation.Transformation
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import coil.size.Scale
import coil.transform.CircleCropTransformation
import com.example.movie_app_compose.BuildConfig
import com.example.movie_app_compose.R
import com.example.movie_app_compose.ui.theme.DarkBlue900
import com.example.movie_app_compose.ui.theme.MovieAppComposeTheme
import com.example.movie_app_compose.util.Const
import java.io.File

@ExperimentalCoilApi
@Composable
fun LazyRowLandscapeItem(
    modifier: Modifier = Modifier,
    imageUrl: String = "",
    title: String = "",
    date: String = "",
    voteAverage: Float = 0f,
    typeRepo : String = Const.TYPE_REPO_REMOTE
) {
    var imageUri by remember { mutableStateOf(Uri.fromFile(File(imageUrl))) }
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }

    ConstraintLayout(modifier = modifier) {
        val (tvTitle, tvReleaseDate, rating, surfaceImage) = createRefs()
        val fullUrlImage = "${BuildConfig.BASE_IMAGE_URL}$imageUrl"
        val data = rememberImagePainter(
            data = fullUrlImage,
            builder = {
                error(R.color.darkblue)
                placeholder(R.color.darkblue)
                crossfade(true)
            })

        Surface(
            shape = RoundedCornerShape(4.dp),
            modifier = modifier.constrainAs(surfaceImage) {
                top.linkTo(parent.top)
            }) {
            when(typeRepo){
                Const.TYPE_REPO_REMOTE -> {
                    Image(
                        painter = data,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = modifier
                            .width(200.dp)
                            .height(123.dp)
                    )
                }
                Const.TYPE_ONTHEAIR_LOCAL -> {
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
                                .width(200.dp)
                                .height(123.dp)
                        )
                    }
                }
            }
        }

        TextComponent(
            title,
            style = MaterialTheme.typography.subtitle1,
            fontWeight = FontWeight.Bold,
            modifier = modifier
                .constrainAs(tvTitle) {
                    start.linkTo(surfaceImage.start)
                    top.linkTo(surfaceImage.bottom, 20.dp)
                }
                .width(200.dp)
        )

        TextComponent(
            date,
            style = MaterialTheme.typography.caption,
            modifier = modifier
                .constrainAs(tvReleaseDate) {
                    top.linkTo(tvTitle.bottom, 4.dp)
                    start.linkTo(surfaceImage.start)
                }
        )

        val progress by remember { mutableStateOf(voteAverage.div(10)) }
        val animatedProgress by animateFloatAsState(
            targetValue = progress,
            animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
        )

        ConstraintLayout(modifier = modifier.constrainAs(rating) {
            top.linkTo(surfaceImage.bottom)
            bottom.linkTo(surfaceImage.bottom)
            end.linkTo(surfaceImage.end, 2.dp)
        }) {
            val (tvProgress, CPIndicator) = createRefs()
            Surface(
                modifier = modifier.constrainAs(CPIndicator) {}, color = DarkBlue900,
                shape = CircleShape
            ) {
                CircularProgressIndicator(
                    progress = animatedProgress, strokeWidth = 3.dp,
                    modifier = modifier
                )
            }

            TextComponent(
                value = "${animatedProgress.times(100).toInt()}%", modifier = modifier
                    .constrainAs(tvProgress) {
                        centerVerticallyTo(CPIndicator)
                        centerHorizontallyTo(CPIndicator)
                    }
                    .padding(16.dp),
                style = MaterialTheme.typography.caption,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview
@Composable
fun PreviewLazyRowLandscapeItem() {
    MovieAppComposeTheme {
        LazyRowLandscapeItem()
    }
}