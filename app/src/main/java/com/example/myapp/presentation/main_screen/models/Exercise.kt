package com.example.myapp.presentation.main_screen.models

import androidx.compose.ui.graphics.painter.Painter

data class Exercise(
    val name: String,
    val imageUrl: Painter,
    val description: String
)
