package com.appandr.app.ui.model

data class UiColors(
    val background: String = "#0E0F12",
    val surface: String = "#14161A",
    val primary: String = "#00E0FF",
    val onPrimary: String = "#0B0C0F",
    val textPrimary: String = "#FFFFFF",
    val textSecondary: String = "#B9C0CC",
    val cardBorder: String = "#1E2127",
    val liveBadgeBg: String = "#FF1744",
    val liveBadgeText: String = "#FFFFFF",
    val appBarBg: String = "#0E0F12",
    val appBarTitleStart: String = "#00E0FF",
    val appBarTitleEnd: String = "#7C4DFF",
    val statusBar: String = "#0B0C0F",
    val navBar: String = "#0B0C0F",
    val chipBg: String = "#1A1D22",
    val chipText: String = "#C6CCD8",
    val bannerOverlay: String = "#00000080"
)

data class UiTypography(
    val fontFamily: String = "app_font_regular",
    val h1Sp: Float = 22f,
    val h2Sp: Float = 18f,
    val bodySp: Float = 14f,
    val captionSp: Float = 12f
)

data class UiShape(
    val cardRadiusDp: Float = 16f,
    val bannerRadiusDp: Float = 16f,
    val chipRadiusDp: Float = 12f
)

data class UiGrid(
    val gutterDp: Float = 8f,
    val paddingDp: Float = 8f,
    val colsPortrait: Int = 2,
    val colsLandscape: Int = 3,
    val colsTablet: Int = 4
)

data class UiCard(
    val aspectRatio: String = "16:9",
    val titleMaxLines: Int = 1
)

data class UiBanner(
    val aspectRatio: String = "16:9",
    val autoSlide: Boolean = true,
    val slideIntervalMs: Long = 6000
)

data class UiLayout(
    val headerHeightDp: Float = 56f,
    val grid: UiGrid = UiGrid(),
    val card: UiCard = UiCard(),
    val banner: UiBanner = UiBanner()
)

data class UiBadgeLive(
    val text: String = "EN VIVO",
    val position: String = "topLeft",
    val padHdp: Float = 6f,
    val padVdp: Float = 2f,
    val radiusDp: Float = 8f
)

data class UiTokens(
    val colors: UiColors = UiColors(),
    val typography: UiTypography = UiTypography(),
    val shape: UiShape = UiShape(),
    val layout: UiLayout = UiLayout(),
    val badgeLive: UiBadgeLive = UiBadgeLive()
)

data class Animations(
    val appBarTitleGradient: AnimGrad = AnimGrad(),
    val cardHoverLift: AnimLift = AnimLift(),
    val cardPress: AnimScale = AnimScale(1.0f, 0.98f, 80L, "fastOut"),
    val liveBadgePulse: AnimAlpha = AnimAlpha(0.9f, 1.0f, 900L, "easeInOut", "yoyo"),
    val chipsUnderlineSlide: AnimSimple = AnimSimple(200L, "standard"),
    val bannerParallax: AnimParallax = AnimParallax(),
    val skeletonShimmer: AnimShimmer = AnimShimmer(),
    val listEnter: AnimListEnter = AnimListEnter(),
    val buttonHover: AnimScale = AnimScale(1.0f, 1.05f, 120L, "easeOut"),
    val buttonPress: AnimScale = AnimScale(1.0f, 0.96f, 80L, "fastOut")
)

data class AnimGrad(val durationMs: Long = 4000, val easing: String = "linear", val loop: String = "infinite")
data class AnimLift(
    val elevationFrom: Float = 2f,
    val elevationTo: Float = 6f,
    val scaleFrom: Float = 1.0f,
    val scaleTo: Float = 1.03f,
    val durationMs: Long = 160,
    val easing: String = "standard"
)
data class AnimScale(val scaleFrom: Float, val scaleTo: Float, val durationMs: Long, val easing: String)
data class AnimAlpha(val alphaFrom: Float, val alphaTo: Float, val durationMs: Long, val easing: String, val loop: String = "none")
data class AnimSimple(val durationMs: Long, val easing: String)
data class AnimParallax(val shiftPx: Float = 24f, val durationMs: Long = 6000, val easing: String = "linear", val loop: String = "infinite")
data class AnimShimmer(val durationMs: Long = 1200, val angleDeg: Float = 20f, val intensity: Float = 0.35f)
data class AnimListEnter(
    val staggerMs: Long = 40,
    val offsetY: Float = 10f,
    val alphaFrom: Float = 0f,
    val durationMs: Long = 220,
    val easing: String = "standard"
)
