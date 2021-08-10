package com.example.movie_app_compose.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.movie_app_compose.R
import com.example.movie_app_compose.ui.theme.MovieAppComposeTheme

@Composable
fun LazyColumnItem(modifier: Modifier = Modifier) {
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
                    Image(
                        painter = painterResource(id = R.drawable.sample_foto),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = modifier
                            .height(123.dp)
                            .width(80.dp)
                    )
                }

                TextComponent(
                    value = "Fast and Furios F9 Assambler asdasd asdas dasd asd asda sada sd",
                    modifier = modifier.constrainAs(tvTitle) {
                        top.linkTo(poster.top)
                        start.linkTo(poster.end, margin = 16.dp)
                        end.linkTo(parent.end)
                        width = Dimension.preferredWrapContent
                    },
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.subtitle1,
                    textAlign = TextAlign.Start
                )

                Text(
                    text = "Fast and Furios F9 Assambler asldkjsad adlksajda kajsd lkajsd lkasjdlk asdlkj asdkj asdlkjasd lkasjd laksdj laskdj asldkjasdlkj asdlkajsd lkasjd alskdjasdlkasjdlkasd asl askdjlskadj aslkj asdlkjasd lkasdj lkasdj lasdkj asdlkj",
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
                    value = "07-08-2021",
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