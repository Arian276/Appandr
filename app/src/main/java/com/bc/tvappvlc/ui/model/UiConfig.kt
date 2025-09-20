package com.bc.tvappvlc.ui.model

data class UiColors(
    val background: String,
    val surface: String,
    val primary: String,
    val onPrimary: String,
    val textPrimary: String,
    val textSecondary: String,
    val cardBorder: String,
    val liveBadgeBg: String,
    val liveBadgeText: String,
    val appBarBg: String,
    val appBarTitleStart: String,
    val appBarTitleEnd: String,
    val statusBar: String,
    val navBar: String,
    val chipBg: String,
    val chipText: String,
    val bannerOverlay: String
)

data class UiTypography(
    val fontFamily: String,
    val h1Sp: Float,
    val h2Sp: Float,
    val bodySp: Float,
    val captionSp: Float
)

data class UiShape(
    val cardRadiusDp: Float,
    val bannerRadiusDp: Float,
    val chipRadiusDp: Float
)

data class UiGrid(
    val gutterDp: Float,
    val paddingDp: Float,
    val colsPortrait: Int,
    val colsLandscape: Int,
    val colsTablet: Int
)

data class UiCard(
    val aspectRatio: String,
    val titleMaxLines: Int
)

data class UiBanner(
    val aspectRatio: String,
    val autoSlide: Boolean,
    val slideIntervalMs: Long
)

data class UiLayout(
    val headerHeightDp: Float,
    val grid: UiGrid,
    val card: UiCard,
    val banner: UiBanner
)

data class UiBadgeLive(
    val text: String,
    val position: String,
    val padHdp: Float,
    val padVdp: Float,
    val radiusDp: Float
)

data class UiTokens(
    val colors: UiColors,
    val typography: UiTypography,
    val shape: UiShape,
    val layout: UiLayout,
    val badgeLive: UiBadgeLive
)

data class AnimGrad(val durationMs: Long, val easing: String, val loop: String)
data class AnimLift(
    val elevationFrom: Float,
    val elevationTo: Float,
    val scaleFrom: Float,
    val scaleTo: Float,
    val durationMs: Long,
    val easing: String
)
data class AnimScale(val scaleFrom: Float, val scaleTo: Float, val durationMs: Long, val easing: String)
data class AnimAlpha(val alphaFrom: Float, val alphaTo: Float, val durationMs: Long, val easing: String, val loop: String)
data class AnimSimple(val durationMs: Long, val easing: String)
data class AnimParallax(val shiftPx: Float, val durationMs: Long, val easing: String, val loop: String)
data class AnimShimmer(val durationMs: Long, val angleDeg: Float, val intensity: Float)
data class AnimListEnter(val staggerMs: Long, val offsetY: Float, val alphaFrom: Float, val durationMs: Long, val easing: String)

data class Animations(
    val appBarTitleGradient: AnimGrad,
    val cardHoverLift: AnimLift,
    val cardPress: AnimScale,
    val liveBadgePulse: AnimAlpha,
    val chipsUnderlineSlide: AnimSimple,
    val bannerParallax: AnimParallax,
    val skeletonShimmer: AnimShimmer,
    val listEnter: AnimListEnter,
    val buttonHover: AnimScale,
    val buttonPress: AnimScale
)
