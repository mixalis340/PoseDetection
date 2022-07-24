package com.example.myapp.presentation.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = GreenAccent,
    background = DarkGray,
    onBackground = TextWhite,
    onPrimary = DarkGray,
    onSurface = LightGray
)



@Composable
fun MyAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = DarkColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}