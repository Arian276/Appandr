package com.bc.tvappvlc.ui

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bc.tvappvlc.R

// 🎨 Colores base
object ColorTokens {
    val Background = Color(0xFF000000)     // negro fondo
    val Surface = Color(0xFF111111)        // gris oscuro
    val Primary = Color(0xFF00D1FF)        // acento
    val OnPrimary = Color(0xFF000000)      // texto sobre acento
    val TextPrimary = Color(0xFFFFFFFF)    // texto principal
    val TextSecondary = Color(0xFF9AA0A6)  // texto secundario
    val Error = Color(0xFFFF3B30)          // rojo
}

// 🔤 Tipografía
val AppFontFamily = FontFamily(
    Font(R.font.inter_regular),
    Font(R.font.inter_italic),
    Font(R.font.inter_bold) // ⚠️ este archivo debe existir en res/font/
)

// 📏 Dimensiones
object Dimens {
    val ScreenPadding = 16.dp
    val CardRadius = 12.dp
    val CardElevation = 4.dp
    val ChannelLogoSize = 64.dp
}

// 🔠 Tamaños de texto
object TextSizes {
    val Title = 20.sp
    val Subtitle = 14.sp
    val Body = 16.sp
    val Small = 12.sp
}
