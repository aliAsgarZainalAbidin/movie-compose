package com.example.movie_app_compose.ui.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.example.movie_app_compose.ui.theme.Green500
import com.example.movie_app_compose.ui.theme.Green700

@Preview
@Composable
fun OutlinedTextFieldComponent(
    value: String = "",
    name: String = "Username",
    passwordVisualTransform: Boolean = false,
    keyboardOptionsType: KeyboardType = KeyboardType.Text,
    modifier: Modifier = Modifier,
    updateValue : (String) -> Unit = {}
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

@Preview
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