package com.example.movie_app_compose.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.movie_app_compose.R
import com.example.movie_app_compose.model.People
import com.example.movie_app_compose.ui.theme.Green500
import com.example.movie_app_compose.ui.theme.Green800
import com.example.movie_app_compose.ui.theme.MovieAppComposeTheme

@Composable
fun LazyRowPopularItem(modifier: Modifier = Modifier, people: People) {
    Column(modifier = modifier.width(IntrinsicSize.Min)) {
//        val (surface, text) = createRefs()
        Surface(
            modifier = modifier
                .clip(CircleShape)
                .background(Green500)
                .padding(4.dp)
                .width(80.dp)
                .height(80.dp), shape = CircleShape
        ) {
            Image(
                painter = painterResource(id = R.drawable.example_photo_profile),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
        }

        TextComponent(
            value = "${people.name}",
            modifier = modifier.fillMaxWidth(),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.caption,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun PreviewLazyRowPopularItem() {
    MovieAppComposeTheme {
        LazyRowPopularItem(people = People())
    }
}