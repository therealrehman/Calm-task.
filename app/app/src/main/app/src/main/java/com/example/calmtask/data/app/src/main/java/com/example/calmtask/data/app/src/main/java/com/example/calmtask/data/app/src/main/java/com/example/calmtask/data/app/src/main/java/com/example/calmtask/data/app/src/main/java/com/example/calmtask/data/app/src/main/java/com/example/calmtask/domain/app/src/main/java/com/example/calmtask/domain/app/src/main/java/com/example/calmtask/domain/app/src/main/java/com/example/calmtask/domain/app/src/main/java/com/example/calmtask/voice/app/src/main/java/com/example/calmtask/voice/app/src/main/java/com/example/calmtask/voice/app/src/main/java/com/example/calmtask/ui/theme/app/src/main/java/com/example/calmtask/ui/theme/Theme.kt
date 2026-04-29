package com.example.calmtask.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val CalmColorScheme = lightColorScheme(
    primary          = PrimaryBlue,
    onPrimary        = Color.White,
    secondary        = CalmGreen,
    onSecondary      = Color.White,
    background       = WarmBackground,
    onBackground     = Charcoal,
    surface          = CardWhite,
    onSurface        = Charcoal,
    tertiary         = WarmAmber
)

@Composable
fun CalmTaskTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = CalmColorScheme,
        typography  = AppTypography,
        content     = content
    )
}
