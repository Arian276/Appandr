package com.bc.tvappvlc.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.bc.tvappvlc.ui.InterFontFamily

private val DarkColors = darkColorScheme(
    primary = Color(0xFF00D1FF),
    onPrimary = Color(0xFF000000),
    background = Color(0xFF000000),
    onBackground = Color(0xFFFFFFFF),
    surface = Color(0xFF111111),
    onSurface = Color(0xFFFFFFFF),
    secondary = Color(0xFF9AA0A6),
    onSecondary = Color(0xFFFFFFFF)
)

private val AppTypography = Typography(
    // aplica Inter a todo por defecto
    bodyLarge = androidx.compose.ui.text.TextStyle(fontFamily = InterFontFamily),
    titleLarge = androidx.compose.ui.text.TextStyle(fontFamily = InterFontFamily),
    labelSmall = androidx.compose.ui.text.TextStyle(fontFamily = InterFontFamily)
)

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColors,
        typography = AppTypography,
        content = content
    )
}
