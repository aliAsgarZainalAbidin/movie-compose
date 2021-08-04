package com.example.movie_app_compose.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.movie_app_compose.ui.theme.DarkBlue900
import com.example.movie_app_compose.ui.theme.Green500
import com.example.movie_app_compose.ui.theme.Green700

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
    style: TextStyle = MaterialTheme.typography.body2
) {
    Text(
        text = value,
        modifier = modifier,
        fontWeight = fontWeight,
        color = color,
        maxLines = 1,
        style = style,
        overflow = TextOverflow.Ellipsis
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