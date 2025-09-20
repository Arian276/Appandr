package com.bc.tvappvlc.theme

import android.content.Context
import android.graphics.Color
import android.widget.TextView
import com.bc.tvappvlc.model.RemoteConfig

object ThemeManager {

    data class Colors(
        val primary: Int,
        val onPrimary: Int,
        val background: Int,
        val surface: Int,
        val surfaceVariant: Int,
        val onSurface: Int,
        val muted: Int,
        val stroke: Int
    )

    fun color(hex: String?, fallback: Int): Int =
        try { if (hex.isNullOrBlank()) fallback else Color.parseColor(hex) }
        catch (_: Exception) { fallback }

    fun resolveColors(cfg: RemoteConfig, ctx: Context): Colors {
        val t = cfg.branding?.theme
        return Colors(
            primary        = color(t?.primary,        Color.parseColor("#00C2A8")),
            onPrimary      = color(t?.onPrimary,      Color.WHITE),
            background     = color(t?.background,     Color.parseColor("#101015")),
            surface        = color(t?.surface,        Color.parseColor("#15161B")),
            surfaceVariant = color(t?.surfaceVariant, Color.parseColor("#1E222B")),
            onSurface      = color(t?.onSurface,      Color.parseColor("#DDE3EA")),
            muted          = color(t?.muted,          Color.parseColor("#9AA3AD")),
            stroke         = color(t?.stroke_color,   Color.parseColor("#2A2F36"))
        )
    }

    fun applyToolbarTitleStyle(view: TextView?, cfg: RemoteConfig) {
        view ?: return
        val typo = cfg.typography
        typo?.title_size_sp?.let { view.textSize = it.toFloat() }
        typo?.title_letter_spacing_em?.let { view.letterSpacing = it }
        typo?.title_color?.let { view.setTextColor(color(it, view.currentTextColor)) }
    }
}