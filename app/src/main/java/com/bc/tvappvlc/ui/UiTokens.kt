package com.bc.tvappvlc.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.bc.tvappvlc.R
import com.bc.tvappvlc.ui.model.UiTokens
import com.bc.tvappvlc.ui.model.Animations

object ThemeTokens {
    lateinit var tokens: UiTokens
    lateinit var anims: Animations
}

private val inter = FontFamily(
    Font(R.font.inter_regular),
    Font(R.font.inter_medium, FontWeight.Medium),
    Font(R.font.inter_bold, FontWeight.Bold)
)

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = darkColorScheme(),
        typography = MaterialTheme.typography.copy(defaultFontFamily = inter),
        content = content
    )
}
