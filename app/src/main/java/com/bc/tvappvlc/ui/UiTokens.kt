package com.appandr.app.ui

import android.content.Context
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.appandr.app.ui.model.*
import org.json.JSONObject
import java.nio.charset.Charset

// ====== CompositionLocals ======
data class AppThemeTokens(
    val tokens: UiTokens,
    val animations: Animations
)

private val LocalAppThemeTokens = staticCompositionLocalOf {
    AppThemeTokens(UiTokens(), Animations())
}

@Composable
fun AppTheme(
    content: @Composable () -> Unit
) {
    val ctx = LocalContext.current
    val tokens = remember { loadTokens(ctx) }
    val anims = remember { loadAnimations(ctx) }

    val colors = tokens.colors
    val scheme = lightColorScheme(
        primary = Color(parseHex(colors.primary)),
        onPrimary = Color(parseHex(colors.onPrimary)),
        background = Color(parseHex(colors.background)),
        surface = Color(parseHex(colors.surface)),
        onSurface = Color(parseHex(colors.textPrimary))
    )

    val typography = Typography(
        displayLarge = TextStyle(
            fontSize = tokens.typography.h1Sp.sp,
            lineHeight = (tokens.typography.h1Sp + 8).sp,
            fontFamily = FontFamily.Default,
            platformStyle = PlatformTextStyle(includeFontPadding = false)
        ),
        titleMedium = TextStyle(
            fontSize = tokens.typography.h2Sp.sp,
            lineHeight = (tokens.typography.h2Sp + 6).sp,
            fontFamily = FontFamily.Default,
            platformStyle = PlatformTextStyle(includeFontPadding = false)
        ),
        bodyMedium = TextStyle(
            fontSize = tokens.typography.bodySp.sp,
            lineHeight = (tokens.typography.bodySp + 6).sp,
            fontFamily = FontFamily.Default,
            platformStyle = PlatformTextStyle(includeFontPadding = false)
        ),
        labelSmall = TextStyle(
            fontSize = tokens.typography.captionSp.sp,
            lineHeight = (tokens.typography.captionSp + 4).sp,
            fontFamily = FontFamily.Default,
            platformStyle = PlatformTextStyle(includeFontPadding = false)
        )
    )

    CompositionLocalProvider(
        LocalAppThemeTokens provides AppThemeTokens(tokens, anims)
    ) {
        MaterialTheme(
            colorScheme = scheme,
            typography = typography,
            shapes = Shapes(
                small = androidx.compose.foundation.shape.RoundedCornerShape(tokens.shape.chipRadiusDp.dp),
                medium = androidx.compose.foundation.shape.RoundedCornerShape(tokens.shape.cardRadiusDp.dp),
                large = androidx.compose.foundation.shape.RoundedCornerShape(tokens.shape.bannerRadiusDp.dp)
            ),
            content = content
        )
    }
}

// ====== Access helpers ======
object ThemeTokens {
    val tokens: UiTokens
        @Composable get() = LocalAppThemeTokens.current.tokens
    val anims: Animations
        @Composable get() = LocalAppThemeTokens.current.animations
}

// ====== Loaders (org.json minimal) ======
private fun loadTokens(context: Context): UiTokens {
    val json = context.assets.open("tokens.json").use { it.readBytes().toString(Charset.defaultCharset()) }
    val obj = JSONObject(json)

    fun c(key: String, def: String) = obj.getJSONObject("colors").optString(key, def)
    fun f(parent: String, key: String, def: Float) = obj.getJSONObject(parent).optDouble(key, def.toDouble()).toFloat()
    fun i(parent: String, key: String, def: Int) = obj.getJSONObject(parent).optInt(key, def)

    val colors = UiColors(
        background = c("background", "#0E0F12"),
        surface = c("surface", "#14161A"),
        primary = c("primary", "#00E0FF"),
        onPrimary = c("onPrimary", "#0B0C0F"),
        textPrimary = c("textPrimary", "#FFFFFF"),
        textSecondary = c("textSecondary", "#B9C0CC"),
        cardBorder = c("cardBorder", "#1E2127"),
        liveBadgeBg = c("liveBadgeBg", "#FF1744"),
        liveBadgeText = c("liveBadgeText", "#FFFFFF"),
        appBarBg = c("appBarBg", "#0E0F12"),
        appBarTitleStart = c("appBarTitleStart", "#00E0FF"),
        appBarTitleEnd = c("appBarTitleEnd", "#7C4DFF"),
        statusBar = c("statusBar", "#0B0C0F"),
        navBar = c("navBar", "#0B0C0F"),
        chipBg = c("chipBg", "#1A1D22"),
        chipText = c("chipText", "#C6CCD8"),
        bannerOverlay = c("bannerOverlay", "#00000080")
    )

    val typo = obj.getJSONObject("typography")
    val typography = UiTypography(
        fontFamily = typo.optString("fontFamily", "app_font_regular"),
        h1Sp = typo.optDouble("h1Sp", 22.0).toFloat(),
        h2Sp = typo.optDouble("h2Sp", 18.0).toFloat(),
        bodySp = typo.optDouble("bodySp", 14.0).toFloat(),
        captionSp = typo.optDouble("captionSp", 12.0).toFloat()
    )

    val shape = UiShape(
        cardRadiusDp = f("shape", "cardRadiusDp", 16f),
        bannerRadiusDp = f("shape", "bannerRadiusDp", 16f),
        chipRadiusDp = f("shape", "chipRadiusDp", 12f)
    )

    val grid = UiGrid(
        gutterDp = obj.getJSONObject("layout").getJSONObject("grid").optDouble("gutterDp", 8.0).toFloat(),
        paddingDp = obj.getJSONObject("layout").getJSONObject("grid").optDouble("paddingDp", 8.0).toFloat(),
        colsPortrait = obj.getJSONObject("layout").getJSONObject("grid").optInt("colsPortrait", 2),
        colsLandscape = obj.getJSONObject("layout").getJSONObject("grid").optInt("colsLandscape", 3),
        colsTablet = obj.getJSONObject("layout").getJSONObject("grid").optInt("colsTablet", 4)
    )

    val card = UiCard(
        aspectRatio = obj.getJSONObject("layout").getJSONObject("card").optString("aspectRatio", "16:9"),
        titleMaxLines = obj.getJSONObject("layout").getJSONObject("card").optInt("titleMaxLines", 1)
    )

    val banner = UiBanner(
        aspectRatio = obj.getJSONObject("layout").getJSONObject("banner").optString("aspectRatio", "16:9"),
        autoSlide = obj.getJSONObject("layout").getJSONObject("banner").optBoolean("autoSlide", true),
        slideIntervalMs = obj.getJSONObject("layout").getJSONObject("banner").optLong("slideIntervalMs", 6000)
    )

    val layout = UiLayout(
        headerHeightDp = obj.getJSONObject("layout").optDouble("headerHeightDp", 56.0).toFloat(),
        grid = grid, card = card, banner = banner
    )

    val bl = obj.getJSONObject("badgeLive")
    val badgeLive = UiBadgeLive(
        text = bl.optString("text", "EN VIVO"),
        position = bl.optString("position", "topLeft"),
        padHdp = bl.optDouble("padHdp", 6.0).toFloat(),
        padVdp = bl.optDouble("padVdp", 2.0).toFloat(),
        radiusDp = bl.optDouble("radiusDp", 8.0).toFloat()
    )

    return UiTokens(colors, typography, shape, layout, badgeLive)
}

private fun loadAnimations(context: Context): Animations {
    val json = context.assets.open("animations.json").use { it.readBytes().toString(Charset.defaultCharset()) }
    val obj = JSONObject(json)

    fun simple(name: String, defDur: Long, defEasing: String) =
        AnimSimple(obj.optJSONObject(name)?.optLong("durationMs", defDur) ?: defDur,
            obj.optJSONObject(name)?.optString("easing", defEasing) ?: defEasing)

    val grad = obj.optJSONObject("appBarTitleGradient")?.let {
        AnimGrad(it.optLong("durationMs", 4000), it.optString("easing", "linear"), it.optString("loop", "infinite"))
    } ?: AnimGrad()

    val lift = obj.optJSONObject("cardHoverLift")?.let {
        AnimLift(
            it.optDouble("elevationFrom", 2.0).toFloat(),
            it.optDouble("elevationTo", 6.0).toFloat(),
            it.optDouble("scaleFrom", 1.0).toFloat(),
            it.optDouble("scaleTo", 1.03).toFloat(),
            it.optLong("durationMs", 160),
            it.optString("easing", "standard")
        )
    } ?: AnimLift()

    val press = obj.optJSONObject("cardPress")?.let {
        AnimScale(it.optDouble("scaleFrom", 1.0).toFloat(), it.optDouble("scaleTo", 0.98).toFloat(), it.optLong("durationMs", 80), it.optString("easing", "fastOut"))
    } ?: AnimScale(1.0f, 0.98f, 80L, "fastOut")

    val lb = obj.optJSONObject("liveBadgePulse")?.let {
        AnimAlpha(it.optDouble("alphaFrom", .9).toFloat(), it.optDouble("alphaTo", 1.0).toFloat(), it.optLong("durationMs", 900), it.optString("easing", "easeInOut"), it.optString("loop", "yoyo"))
    } ?: AnimAlpha(.9f, 1f, 900L, "easeInOut", "yoyo")

    val parallax = obj.optJSONObject("bannerParallax")?.let {
        AnimParallax(it.optDouble("shiftPx", 24.0).toFloat(), it.optLong("durationMs", 6000), it.optString("easing", "linear"), it.optString("loop", "infinite"))
    } ?: AnimParallax()

    val shimmer = obj.optJSONObject("skeletonShimmer")?.let {
        AnimShimmer(it.optLong("durationMs", 1200), it.optDouble("angleDeg", 20.0).toFloat(), it.optDouble("intensity", .35).toFloat())
    } ?: AnimShimmer()

    val listEnter = obj.optJSONObject("listEnter")?.let {
        AnimListEnter(it.optLong("staggerMs", 40), it.optDouble("offsetY", 10.0).toFloat(), it.optDouble("alphaFrom", 0.0).toFloat(), it.optLong("durationMs", 220), it.optString("easing", "standard"))
    } ?: AnimListEnter()

    val btnHover = obj.optJSONObject("buttonHover")?.let {
        AnimScale(it.optDouble("scaleFrom", 1.0).toFloat(), it.optDouble("scaleTo", 1.05).toFloat(), it.optLong("durationMs", 120), it.optString("easing", "easeOut"))
    } ?: AnimScale(1f, 1.05f, 120L, "easeOut")

    val btnPress = obj.optJSONObject("buttonPress")?.let {
        AnimScale(it.optDouble("scaleFrom", 1.0).toFloat(), it.optDouble("scaleTo", 0.96).toFloat(), it.optLong("durationMs", 80), it.optString("easing", "fastOut"))
    } ?: AnimScale(1f, .96f, 80L, "fastOut")

    return Animations(
        appBarTitleGradient = grad,
        cardHoverLift = lift,
        cardPress = press,
        liveBadgePulse = lb,
        chipsUnderlineSlide = simple("chipsUnderlineSlide", 200L, "standard"),
        bannerParallax = parallax,
        skeletonShimmer = shimmer,
        listEnter = listEnter,
        buttonHover = btnHover,
        buttonPress = btnPress
    )
}

// ===== Utils =====
private fun parseHex(hex: String): Long {
    val clean = hex.removePrefix("#")
    val full = if (clean.length == 6) "FF$clean" else clean
    return full.toLong(16)
}
