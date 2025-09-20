package com.bc.tvappvlc.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bc.tvappvlc.R
import com.bc.tvappvlc.model.Channel
import com.bc.tvappvlc.model.RemoteConfig
import com.bc.tvappvlc.theme.ThemeManager
import com.google.android.material.card.MaterialCardView

class ChannelAdapter(
    private val items: MutableList<Channel>,
    private val onClick: (Channel) -> Unit
) : RecyclerView.Adapter<ChannelAdapter.Holder>() {

    private var cfg: RemoteConfig? = null
    private var colors: ThemeManager.Colors? = null

    fun submitTheme(c: RemoteConfig) {
        cfg = c
        colors = null
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_channel, parent, false)
        return Holder(v)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(h: Holder, position: Int) {
        val ch = items[position]
        val ctx = h.itemView.context

        if (colors == null && cfg != null) {
            colors = ThemeManager.resolveColors(cfg!!, ctx)
        }

        // Imagen
        if (!ch.logo.isNullOrBlank()) h.logo.load(ch.logo) else h.logo.setImageDrawable(null)

        // Textos base
        h.name.text = ch.name
        h.category.text = ch.category ?: "Deportes"

        // Badge de resolución (ej. 1080p)
        if (ch.resolution.isNullOrBlank()) {
            h.resolution.visibility = View.GONE
        } else {
            h.resolution.visibility = View.VISIBLE
            h.resolution.text = ch.resolution
        }

        // CTA
        h.cta.text = cfg?.strings?.cta_watch ?: "VER AHORA"
        cfg?.layout?.card?.cta_text_size_sp?.let { h.cta.textSize = it.toFloat() }
        h.cta.setOnClickListener { onClick(ch) }
        h.card.setOnClickListener { onClick(ch) }

        // Colores desde ThemeManager
        colors?.let { c ->
            h.card.setCardBackgroundColor(c.surface)
            h.card.strokeColor = c.stroke

            h.name.setTextColor(c.onSurface)
            h.category.setTextColor(c.muted)
            h.resolution.setTextColor(c.onSurface)
            // Si querés, podés pintar el fondo del CTA con c.primary aquí.
        }
    }

    class Holder(v: View) : RecyclerView.ViewHolder(v) {
        val card: MaterialCardView = v.findViewById(R.id.card)
        val logo: ImageView = v.findViewById(R.id.imgLogo)
        val name: TextView = v.findViewById(R.id.txtName)
        val category: TextView = v.findViewById(R.id.txtCategory)
        val resolution: TextView = v.findViewById(R.id.txtResolution)
        val cta: TextView = v.findViewById(R.id.btnWatch)
    }
}