package com.example.movie_app_compose.ui.theme

import androidx.compose.material.darkColors
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

val Green500 = Color(0xFF1EB980)
val Grey = Color(0xFFC5C5C5)
val Green600 = Color(0xFF00897B)
val Green700 = Color(0xFF00796B)
val Green800 = Color(0xFF00695C)
val DarkBlue900 = Color(0xFF26282F)

// Rally is always dark themed.
val ColorPalette = darkColors(
    primary = Green500,
    surface = DarkBlue900,
    onSurface = Color.White,
    background = DarkBlue900,
    onBackground = Color.White
)