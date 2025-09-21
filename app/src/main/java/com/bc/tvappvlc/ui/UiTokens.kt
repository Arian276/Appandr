package com.bc.tvappvlc.ui

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.bc.tvappvlc.R

// Definimos la familia tipográfica usando los archivos en res/font/
val InterFontFamily = FontFamily(
    Font(R.font.inter_regular, weight = FontWeight.Normal),
    Font(R.font.inter_bold, weight = FontWeight.Bold),
    Font(R.font.inter_italic, weight = FontWeight.Normal) // estilo italic
)

// Tokens centralizados de la UI
object ThemeTokens {
    val titleLarge = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 28.sp
    )

    val bodyLarge = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp
    )

    val labelSmall = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp
    )
}

// Tipografía general de Compose (opcional, si querés integrarlo en Theme)
val AppTypography = Typography(
    bodyLarge = ThemeTokens.bodyLarge,
    titleLarge = ThemeTokens.titleLarge,
    labelSmall = ThemeTokens.labelSmall
)
