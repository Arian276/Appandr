package com.bc.tvappvlc.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.TextStyle

// 📏 Dimensiones globales
object UiTokens {
    val RadiusSmall = 4.dp
    val RadiusMedium = 8.dp
    val RadiusLarge = 12.dp

    val PaddingSmall = 4.dp
    val PaddingMedium = 8.dp
    val PaddingLarge = 16.dp

    val CardElevation = 2.dp
}

// 🎨 Colores para reutilizar
object UiColors {
    val BadgeBg = Color(0xFF2C2C2C)
    val Primary = Color(0xFF00BCD4)
    val PrimaryRipple = Color(0xFF0097A7)
    val TextPrimary = Color(0xFFFFFFFF)
    val TextSecondary = Color(0xFFAAAAAA)
    val OnBadge = Color(0xFFFFFFFF)
}

// 🔤 Estilos de texto
object UiTypography {
    val Title = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        color = UiColors.TextPrimary
    )
    val Subtitle = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp,
        color = UiColors.TextSecondary
    )
    val Badge = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        color = UiColors.OnBadge
    )
}
