package com.example.movie_app_compose.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.movie_app_compose.ui.theme.DarkBlue900
import com.example.movie_app_compose.ui.theme.Green500
import com.example.movie_app_compose.ui.theme.Green700
import kotlin.math.max

@Composable
fun OutlinedTextFieldComponent(
    value: String = "",
    name: String = "Username",
    passwordVisualTransform: Boolean = false,
    keyboardOptionsType: KeyboardType = KeyboardType.Text,
    modifier: Modifier = Modifier,
    updateValue: (String) -> Unit = {}
) {
    OutlinedTextField(
        value = value,
        onValueChange = { updateValue(it) },
        label = { Text(name) },
        maxLines = 1,
        visualTransformation = if (passwordVisualTransform)
            PasswordVisualTransformation()
        else
            VisualTransformation.None,
        keyboardOptions = if (passwordVisualTransform)
            KeyboardOptions(keyboardType = KeyboardType.Password)
        else
            KeyboardOptions(keyboardType = keyboardOptionsType),
        modifier = modifier,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Green500,
            unfocusedBorderColor = Green700
        )
    )
}

@Composable
fun ButtonComponent(
    title: String = "Button",
    onButtonClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = MaterialTheme.shapes.small
) {
    Button(
        onClick = { onButtonClick() },
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        colors = ButtonDefaults.buttonColors(backgroundColor = Green500)
    ) {
        Text(text = title, style = MaterialTheme.typography.button, color = Color.White)
    }
}

@Composable
fun TextComponent(
    value: String = "",
    modifier: Modifier = Modifier,
    fontWeight: FontWeight = FontWeight.Normal,
    color: Color = Color.White,
    style: TextStyle = MaterialTheme.typography.body2,
    textAlign: TextAlign = TextAlign.Start,
    maxLines: Int = 1
) {
    Text(
        text = value,
        modifier = modifier,
        fontWeight = fontWeight,
        color = color,
        maxLines = maxLines,
        style = style,
        overflow = TextOverflow.Ellipsis,
        textAlign = textAlign
    )
}

@Composable
fun TextComponent(
    value: AnnotatedString = buildAnnotatedString { },
    modifier: Modifier = Modifier,
    fontWeight: FontWeight = FontWeight.Normal,
    color: Color = Color.White,
    style: TextStyle = MaterialTheme.typography.body2,
    textAlign: TextAlign = TextAlign.Start,
    maxLines: Int = 1
) {
    Text(
        text = value,
        modifier = modifier,
        fontWeight = fontWeight,
        color = color,
        maxLines = maxLines,
        style = style,
        overflow = TextOverflow.Ellipsis,
        textAlign = textAlign
    )
}

@Composable
fun CircularProgressIndicatorComponent(
    animatedProgress: Float = 0.1f,
    modifier: Modifier = Modifier
) {
    ConstraintLayout(modifier = modifier) {
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
            value = "78%", modifier = modifier
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

@Composable
fun Chip(modifier: Modifier = Modifier, text: String) {
    Card(
        modifier = modifier,
        border = BorderStroke(
            color = Green500,
            width = Dp.Hairline
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(start = 8.dp, top = 4.dp, end = 8.dp, bottom = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp, 8.dp)
                    .background(color = Green500)
            )
            Spacer(Modifier.width(4.dp))
            TextComponent(
                value = text,
                style = MaterialTheme.typography.overline
            )
        }
    }
    Spacer(Modifier.width(4.dp))
}

@Composable
fun GridLayout(
    modifier: Modifier = Modifier,
    rows: Int = 2,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        // measure and position children given constraints logic here
        val rowWidths = IntArray(rows) { 0 }

        // Keep track of the max height of each row
        val rowHeights = IntArray(rows) { 0 }

        // Don't constrain child views further, measure them with given constraints
        // List of measured children
        val placeables = measurables.mapIndexed { index, measurable ->

            // Measure each child
            val placeable = measurable.measure(constraints)

            // Track the width and max height of each row
            val row = index % rows
//            rowWidths[row] += placeable.width
//            rowHeights[row] = max(rowHeights[row], placeable.height)

            rowWidths[row] = max(rowWidths[row], placeable.width)
            rowHeights[row] += placeable.height

            placeable
        }
        // Grid's width is the widest row
        val width = rowWidths.maxOrNull()
            ?.coerceIn(constraints.minWidth.rangeTo(constraints.maxWidth)) ?: constraints.minWidth

        val widthC = constraints.maxWidth

        // Grid's height is the sum of the tallest element of each row
        // coerced to the height constraints
        val height = rowHeights.sumOf { it }
            .coerceIn(constraints.minHeight.rangeTo(constraints.maxHeight))

        val heightC = rowHeights.maxOrNull()
            ?.coerceIn(constraints.minHeight.rangeTo(constraints.maxHeight))
            ?: constraints.minHeight

        // Y of each row, based on the height accumulation of previous rows
        val rowX = IntArray(rows) { 0 }
        for (i in 1 until rows) {
            rowX[i] = rowX[i - 1] + rowWidths[i - 1]
        }

        // Set the size of the parent layout
        layout(widthC, height) {
            // x cord we have placed up to, per row
            val rowY = IntArray(rows) { 0 }

            placeables.forEachIndexed { index, placeable ->
                val row = index % rows
                placeable.placeRelative(
                    x = rowX[row],
                    y = rowY[row]
                )
                rowY[row] += placeable.height
            }
        }
    }
}


