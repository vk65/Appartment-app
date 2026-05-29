package com.example.appartmentapp.presentation

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val NoBrokerRed = Color(0xFFE53935)
val NoBrokerDarkRed = Color(0xFFB71C1C)
val NoBrokerLightRed = Color(0xFFFFEBEE)

private val LightColorScheme = lightColorScheme(
    primary = NoBrokerRed,
    onPrimary = Color.White,
    primaryContainer = NoBrokerLightRed,
    onPrimaryContainer = NoBrokerDarkRed,
    secondary = Color(0xFF424242),
    onSecondary = Color.White,
    background = Color(0xFFF5F5F5),
    surface = Color.White,
    error = Color(0xFFD32F2F)
)

private val DarkColorScheme = darkColorScheme(
    primary = NoBrokerRed,
    onPrimary = Color.White,
    secondary = Color(0xFFBDBDBD),
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E)
)

@Composable
fun NoBrokerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
