package com.bc.tvappvlc.ui

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bc.tvappvlc.R

/**
 * Tokens de diseño compartidos en toda la app.
 * Si querés cambiar un color, fuente o tamaño -> lo hacés acá.
 */

// 🎨 Colores base
object ColorTokens {
    val Background = Color(0xFF000000)     // negro (fondo)
    val Surface = Color(0xFF111111)        // gris oscuro (tarjetas)
    val Primary = Color(0xFF00D1FF)        // acento (similar a tu web)
    val OnPrimary = Color(0xFF000000)      // texto sobre acento
    val TextPrimary = Color(0xFFFFFFFF)    // texto principal
    val TextSecondary = Color(0xFF9AA0A6)  // texto secundario
    val Error = Color(0xFFFF3B30)          // rojo
}

// 🔤 Tipografías (usando tus fuentes en res/font/)
val defaultFontFamily = FontFamily(
    Font(R.font.inter_regular),
    Font(R.font.inter_italic),
    Font(R.font.inter_bold) // si agregás inter_bold
