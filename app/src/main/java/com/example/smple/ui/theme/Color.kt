package com.example.smple.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val GreenPrimary = Color(0xFF34C759)
val GreenDark = Color(0xFF28A044)
val GreenMid = Color(0xFF34C759)
val TextDark = Color(0xFF1C1C1E)

val AppBackgroundGradient = Brush.verticalGradient(
    colorStops = arrayOf(
        0.0f to Color(0xFF34C759),
        0.82f to Color(0xFF34C759),
        1.0f to Color(0xFFFFFFFF),
    )
)
