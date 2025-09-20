package com.bc.tvappvlc.ui

import android.content.Context
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.bc.tvappvlc.R
import com.bc.tvappvlc.ui.model.*
import org.json.JSONObject
import java.nio.charset.Charset

object ThemeTokens {
    lateinit var tokens: UiTokens
    lateinit var anims: Animations
    var initialized: Boolean = false
}

// ===== Tipografía (Inter) =====
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

// ======= LOADERS desde assets (tokens.json / animations.json) =======

fun initThemeTokensIfNeeded(context: Context) {
    if (ThemeTokens.initialized) return
    ThemeTokens.tokens = loadTokens(context)
    ThemeTokens.anims  = loadAnimations(context)
    ThemeTokens.initialized = true
}

private fun loadTokens(ctx: Context): UiTokens {
    val jsonStr = ctx.assets.open("tokens.json").use { it.readBytes().toString(Charset.defaultCharset()) }
    val root = JSONObject(jsonStr)

    fun col(o: JSONObject, k: String, def: String = "#000000") = o.optString(k, def)
    fun flt(o: JSONObject, k: String, def: Double) = o.optDouble(k, def).toFloat()
    fun int(o: JSONObject, k: String, def: Int) = o.optInt(k, def)

    val colorsO = root.getJSONObject("colors")
    val typoO   = root.getJSONObject("typography")
    val shapeO  = root.getJSONObject("shape")
    val layoutO = root.getJSONObject("layout")
    val gridO   = layoutO.getJSONObject("grid")
    val cardO   = layoutO.getJSONObject("card")
    val bannerO = layoutO.getJSONObject("banner")
    val liveO   = root.getJSONObject("badgeLive")

    val colors = UiColors(
        background     = col(colorsO, "background", "#0B0B0B"),
        surface        = col(colorsO, "surface", "#121212"),
        primary        = col(colorsO, "primary", "#1E88E5"),
        onPrimary      = col(colorsO, "onPrimary", "#FFFFFF"),
        textPrimary    = col(colorsO, "textPrimary", "#FFFFFF"),
        textSecondary  = col(colorsO, "textSecondary", "#B3B3B3"),
        cardBorder     = col(colorsO, "cardBorder", "#FFFFFF"),
        liveBadgeBg    = col(colorsO, "liveBadgeBg", "#FF1744"),
        liveBadgeText  = col(colorsO, "liveBadgeText", "#FFFFFF"),
        appBarBg       = col(colorsO, "appBarBg", "#0B0B0B"),
        appBarTitleStart = col(colorsO, "appBarTitleStart", "#5AC8FA"),
        appBarTitleEnd   = col(colorsO, "appBarTitleEnd", "#FFFFFF"),
        statusBar      = col(colorsO, "statusBar", "#000000"),
        navBar         = col(colorsO, "navBar", "#000000"),
        chipBg         = col(colorsO, "chipBg", "#1E1E1E"),
        chipText       = col(colorsO, "chipText", "#FFFFFF"),
        bannerOverlay  = col(colorsO, "bannerOverlay", "#66000000")
    )

    val typo = UiTypography(
        fontFamily = typoO.optString("fontFamily", "inter_regular"),
        h1Sp = flt(typoO, "h1Sp", 28.0),
        h2Sp = flt(typoO, "h2Sp", 22.0),
        bodySp = flt(typoO, "bodySp", 14.0),
        captionSp = flt(typoO, "captionSp", 12.0)
    )

    val shape = UiShape(
        cardRadiusDp = flt(shapeO, "cardRadiusDp", 14.0),
        bannerRadiusDp = flt(shapeO, "bannerRadiusDp", 16.0),
        chipRadiusDp = flt(shapeO, "chipRadiusDp", 12.0)
    )

    val grid = UiGrid(
        gutterDp = flt(gridO, "gutterDp", 10.0),
        paddingDp = flt(gridO, "paddingDp", 12.0),
        colsPortrait = int(gridO, "colsPortrait", 2),
        colsLandscape = int(gridO, "colsLandscape", 4),
        colsTablet = int(gridO, "colsTablet", 6)
    )

    val card = UiCard(
        aspectRatio = cardO.optString("aspectRatio", "16:9"),
        titleMaxLines = int(cardO, "titleMaxLines", 1)
    )

    val banner = UiBanner(
        aspectRatio = bannerO.optString("aspectRatio", "16:9"),
        autoSlide = bannerO.optBoolean("autoSlide", true),
        slideIntervalMs = bannerO.optLong("slideIntervalMs", 5000L)
    )

    val layout = UiLayout(
        headerHeightDp = flt(layoutO, "headerHeightDp", 56.0),
        grid = grid,
        card = card,
        banner = banner
    )

    val badge = UiBadgeLive(
        text = liveO.optString("text", "EN VIVO"),
        position = liveO.optString("position", "top-start"),
        padHdp = flt(liveO, "padHdp", 8.0),
        padVdp = flt(liveO, "padVdp", 4.0),
        radiusDp = flt(liveO, "radiusDp", 8.0)
    )

    return UiTokens(colors, typo, shape, layout, badge)
}

private fun loadAnimations(ctx: Context): Animations {
    val jsonStr = ctx.assets.open("animations.json").use { it.readBytes().toString(Charset.defaultCharset()) }
    val root = JSONObject(jsonStr)

    fun dbl(o: JSONObject, k: String, d: Double) = o.optDouble(k, d)
    fun flt(o: JSONObject, k: String, d: Double) = dbl(o, k, d).toFloat()
    fun str(o: JSONObject, k: String, d: String) = o.optString(k, d)
    fun lng(o: JSONObject, k: String, d: Long) = o.optLong(k, d)

    val appBar = root.getJSONObject("appBarTitleGradient")
    val hover  = root.getJSONObject("cardHoverLift")
    val press  = root.getJSONObject("cardPress")
    val live   = root.getJSONObject("liveBadgePulse")
    val chips  = root.getJSONObject("chipsUnderlineSlide")
    val par    = root.getJSONObject("bannerParallax")
    val shim   = root.getJSONObject("skeletonShimmer")
    val listE  = root.getJSONObject("listEnter")
    val btnH   = root.getJSONObject("buttonHover")
    val btnP   = root.getJSONObject("buttonPress")

    return Animations(
        appBarTitleGradient = AnimGrad(
            durationMs = lng(appBar, "durationMs", 3000L),
            easing = str(appBar, "easing", "linear"),
            loop = str(appBar, "loop", "infinite")
        ),
        cardHoverLift = AnimLift(
            elevationFrom = flt(hover, "elevationFrom", 0.0),
            elevationTo = flt(hover, "elevationTo", 6.0),
            scaleFrom = flt(hover, "scaleFrom", 1.0),
            scaleTo = flt(hover, "scaleTo", 1.02),
            durationMs = lng(hover, "durationMs", 180L),
            easing = str(hover, "easing", "easeOut")
        ),
        cardPress = AnimScale(
            scaleFrom = flt(press, "scaleFrom", 1.0),
            scaleTo = flt(press, "scaleTo", 0.98),
            durationMs = lng(press, "durationMs", 120L),
            easing = str(press, "easing", "easeInOut")
        ),
        liveBadgePulse = AnimAlpha(
            alphaFrom = flt(live, "alphaFrom", 0.6),
            alphaTo   = flt(live, "alphaTo",   1.0),
            durationMs = lng(live, "durationMs", 900L),
            easing = str(live, "easing", "easeInOut"),
            loop = str(live, "loop", "yoyo")
        ),
        chipsUnderlineSlide = AnimSimple(
            durationMs = lng(chips, "durationMs", 220L),
            easing = str(chips, "easing", "easeOut")
        ),
        bannerParallax = AnimParallax(
            shiftPx = flt(par, "shiftPx", 60.0),
            durationMs = lng(par, "durationMs", 8000L),
            easing = str(par, "easing", "linear"),
            loop = str(par, "loop", "infinite")
        ),
        skeletonShimmer = AnimShimmer(
            durationMs = lng(shim, "durationMs", 1800L),
            angleDeg = flt(shim, "angleDeg", 20.0),
            intensity = flt(shim, "intensity", 0.25)
        ),
        listEnter = AnimListEnter(
            staggerMs = lng(listE, "staggerMs", 34L),
            offsetY = flt(listE, "offsetY", 6.0),
            alphaFrom = flt(listE, "alphaFrom", 0.0),
            durationMs = lng(listE, "durationMs", 260L),
            easing = str(listE, "easing", "easeOut")
        ),
        buttonHover = AnimScale(
            scaleFrom = flt(btnH, "scaleFrom", 1.0),
            scaleTo   = flt(btnH, "scaleTo",   1.03),
            durationMs = lng(btnH, "durationMs", 120L),
            easing = str(btnH, "easing", "easeOut")
        ),
        buttonPress = AnimScale(
            scaleFrom = flt(btnP, "scaleFrom", 1.0),
            scaleTo   = flt(btnP, "scaleTo",   0.98),
            durationMs = lng(btnP, "durationMs", 90L),
            easing = str(btnP, "easing", "easeInOut")
        )
    )
}
