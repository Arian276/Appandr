package com.bc.tvappvlc.theme

import android.content.Context
import android.graphics.Typeface

object RemoteFontLoader {
    // Implementable si querés descargar TTF/OTF desde URLs (branding.typography.font_urls).
    // Por ahora devolvé null y usá fuentes locales.
    suspend fun loadTypeface(ctx: Context, regularUrl: String?, boldUrl: String?): Typeface? = null
}