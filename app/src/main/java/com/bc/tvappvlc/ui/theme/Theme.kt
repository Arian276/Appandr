package com.bc.tvappvlc.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Typeface
import androidx.compose.ui.unit.sp

private val DarkColorScheme = darkColorScheme(
    primary = TealGreen,
    onPrimary = Color.Black,
    secondary = TealGreen,
    onSecondary = Color.Black,
    surface = SurfaceDark,
    onSurface = OnSurfaceDark,
    surfaceVariant = SurfaceVariantDark,
    onSurfaceVariant = OnSurfaceVariantDark
)

@Composable
fun TvAppVlcTheme(
    darkTheme: Boolean = true,
    content: @Composable () -> Unit
) {
    // Usamos un tema oscuro siempre
    val colors = DarkColorScheme

    MaterialTheme(
        colorScheme = colors,
        typography = Typography(
            titleLarge = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            ),
            titleMedium = TextStyle(
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
            ),
            bodyLarge = TextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp
            ),
            bodyMedium = TextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 13.sp
            )
        ),
        content = content
    )
}
