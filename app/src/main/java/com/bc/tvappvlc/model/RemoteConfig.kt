package com.bc.tvappvlc.model

data class RemoteConfig(
    val meta: Meta? = null,
    val branding: Branding? = null,
    val strings: Strings? = null,
    val layout: Layout? = null,
    val header: Header? = null,
    val typography: Typography? = null,   // controla el título de la toolbar
    val channels: List<Channel> = emptyList() // usa Channel de Channel.kt
)

data class Meta(
    val version: Int? = null,
    val updated_at: String? = null
)

data class Branding(
    val display_name: String? = null,
    val logo_url: String? = null,
    val banner_url: String? = null,
    val subtitle: String? = null,
    val theme: Theme? = null
)

data class Theme(
    val seed: String? = null,
    val primary: String? = null,
    val onPrimary: String? = null,
    val background: String? = null,
    val surface: String? = null,
    val surfaceVariant: String? = null,
    val onSurface: String? = null,
    val muted: String? = null,
    val stroke_color: String? = null
)

data class Strings(
    val cta_watch: String? = null,
    val search_hint: String? = null,
    val channel_info: String? = null
)

data class Layout(
    val grid_columns: Int? = 2,
    val grid_spacing_dp: Int? = 14,
    val card: CardLayout? = null,
    val home: HomeLayout? = null
)

data class CardLayout(
    val radius_dp: Int? = null,
    val elevation_dp: Int? = null,
    val image_ratio: String? = null,
    val show_badges: Boolean? = null,
    val cta_style: String? = null,
    val cta_text_size_sp: Int? = null
)

data class HomeLayout(
    val show_banner: Boolean? = null,
    val show_title: Boolean? = null
)

data class Header(
    val show_live_badge: Boolean? = null,
    val live_badge_text: String? = null,
    val live_bg: String? = null,
    val live_fg: String? = null
)

/** Tipografía del título de la toolbar editable desde backend */
data class Typography(
    val title_size_sp: Int? = null,
    val title_letter_spacing_em: Float? = null,
    val title_color: String? = null
)