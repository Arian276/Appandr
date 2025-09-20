package com.appandr.app.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun AppPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val t = ThemeTokens.tokens
    val a = ThemeTokens.anims
    val press = a.buttonPress

    var pressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(targetValue = if (pressed) press.scaleTo else 1f, label = "btnScale")

    Button(
        onClick = onClick,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(android.graphics.Color.parseColor(t.colors.primary)),
            contentColor = Color(android.graphics.Color.parseColor(t.colors.onPrimary))
        ),
        interactionSource = remember { MutableInteractionSource() },
        modifier = modifier.scale(scale).padding(4.dp)
    ) {
        Text(text = text, fontWeight = FontWeight.SemiBold)
    }
}
