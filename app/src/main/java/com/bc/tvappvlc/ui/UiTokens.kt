package com.appandr.app.ui

import android.content.Context
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.appandr.app.R
import com.appandr.app.ui.model.*
import org.json.JSONObject
import java.nio.charset.Charset

// ===== CompositionLocal =====
data class AppThemeTokens(val tokens: UiTokens, val animations: Animations)

private val LocalAppThemeTokens = staticCompositionLocalOf {
    AppThemeTokens(UiTokens(
        UiColors("#0E0F12","#14161A","#00E0FF","#0B0C0F","#FFFFFF","#B9C0CC",
            "#1E2127","#FF1744","#FFFFFF","#0E0F12","#00E0FF","#7C4DFF","#0B0C0F","#0B0C0F",
            "#1A1D22","#C6CCD8","#00000080"),
        UiTypography("inter_regular",22f,18f,14f,12f),
        UiShape(16f,16f,12f),
        UiLayout(56f, UiGrid(8f,8f,2,3,4), UiCard("16:9",1), UiBanner("16:9",true,6000)),
        UiBadgeLive("EN VIVO","topLeft",6f,2f,8f)
    ), Animations(
        AnimGrad(4000,"linear","infinite"),
        AnimLift(2f,6f,1f,1.03f,160,"standard"),
        AnimScale(1f,0.98f,80,"fastOut"),
        AnimAlpha(0.9f,1f,900,"easeInOut","yoyo"),
        AnimSimple(200,"standard"),
        AnimParallax(24f,6000,"linear","infinite"),
        AnimShimmer(1200,20f,0.35f),
        AnimListEnter(40,10f,0f,220,"standard"),
        AnimScale(1f,1.05f,120,"easeOut"),
        AnimScale(1f,0.96f,80,"fastOut")
    ))
}

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    val ctx = LocalContext.current
    val tokens = remember { loadTokens(ctx) }
    val anims = remember { loadAnimations(ctx) }

    // Definimos la fuente Inter desde res/font/
    val inter = FontFamily(
        Font(R.font.inter_regular),
        Font(R.font.inter_medium, weight = FontWeight.Medium),
        Font(R.font.inter_bold, weight = FontWeight.Bold)
    )

    val scheme = lightColorScheme(
        primary = Color(parseHex(tokens.colors.primary)),
        onPrimary = Color(parseHex(tokens.colors.onPrimary)),
        background = Color(parseHex(tokens.colors.background)),
        surface = Color(parseHex(tokens.colors.surface)),
        onSurface = Color(parseHex(tokens.colors.textPrimary))
    )

    val typography = Typography(
        displayLarge = TextStyle(fontSize = tokens.typography.h1Sp.sp, fontFamily = inter, platformStyle = PlatformTextStyle(includeFontPadding = false)),
        titleMedium = TextStyle(fontSize = tokens.typography.h2Sp.sp, fontFamily = inter, platformStyle = PlatformTextStyle(includeFontPadding = false)),
        bodyMedium = TextStyle(fontSize = tokens.typography.bodySp.sp, fontFamily = inter, platformStyle = PlatformTextStyle(includeFontPadding = false)),
        labelSmall = TextStyle(fontSize = tokens.typography.captionSp.sp, fontFamily = inter, platformStyle = PlatformTextStyle(includeFontPadding = false))
    )

    CompositionLocalProvider(LocalAppThemeTokens provides AppThemeTokens(tokens, anims)) {
        MaterialTheme(colorScheme = scheme, typography = typography, shapes = Shapes(
            small = androidx.compose.foundation.shape.RoundedCornerShape(tokens.shape.chipRadiusDp.dp),
            medium = androidx.compose.foundation.shape.RoundedCornerShape(tokens.shape.cardRadiusDp.dp),
            large = androidx.compose.foundation.shape.RoundedCornerShape(tokens.shape.bannerRadiusDp.dp)
        ), content = content)
    }
}

object ThemeTokens {
    val tokens: UiTokens @Composable get() = LocalAppThemeTokens.current.tokens
    val anims: Animations @Composable get() = LocalAppThemeTokens.current.animations
}

// ===== Helpers =====
private fun parseHex(hex: String): Long {
    val clean = hex.removePrefix("#")
    val full = if (clean.length == 6) "FF$clean" else clean
    return full.toLong(16)
}

// Loader mínimo (usa org.json)
private fun loadTokens(context: Context): UiTokens {
    val json = context.assets.open("tokens.json").use { it.readBytes().toString(Charset.defaultCharset()) }
    val obj = JSONObject(json)
    // … (igual que la versión larga que te pasé antes, parseando todas las secciones)
    // Para no repetirme, mantené el mismo loader que ya te compartí, solo que ahora devuelve UiTokens con "fontFamily" = "inter_regular".
    return LocalAppThemeTokens.current.tokens
}

private fun loadAnimations(context: Context): Animations {
    val json = context.assets.open("animations.json").use { it.readBytes().toString(Charset.defaultCharset()) }
    val obj = JSONObject(json)
    // … (igual que la versión larga que ya te pasé antes, parseando todos los bloques)
    return LocalAppThemeTokens.current.animations
}
