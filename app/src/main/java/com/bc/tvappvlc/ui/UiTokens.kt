package com.bc.tvappvlc.ui

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bc.tvappvlc.R

// Tipografías
val defaultFontFamily = FontFamily(
    Font(R.font.inter_regular),
    Font(R.font.inter_bold)
)

// Dimensiones comunes
object Dimens {
    val paddingSmall = 4.dp
    val paddingMedium = 8.dp
    val paddingLarge = 16.dp
    val cardRadius = 12.dp
}

// Tamaños de texto
object TextSizes {
    val small = 12.sp
    val medium = 16.sp
    val large = 20.sp
}
