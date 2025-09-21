package com.bc.tvappvlc.ui

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bc.tvappvlc.PlayerActivity
import com.bc.tvappvlc.R
import com.bc.tvappvlc.model.Channel
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton

class ChannelAdapter(
    private val context: Context,
    private val items: List<Channel>
) : RecyclerView.Adapter<ChannelAdapter.VH>() {

    inner class VH(v: View) : RecyclerView.ViewHolder(v) {
        val img: ImageView = v.findViewById(R.id.imgLogo)
        val txtName: TextView = v.findViewById(R.id.txtName)
        val txtDesc: TextView = v.findViewById(R.id.txtDescription)
        val txtQuality: TextView = v.findViewById(R.id.txtQuality)
        val btnWatch: MaterialButton = v.findViewById(R.id.btnWatch)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_channel, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val ch = items[position]

        holder.txtName.text = ch.name
        holder.txtDesc.text = ch.description
        holder.txtQuality.text = ch.quality

        Glide.with(holder.img)
            .load(ch.logo)
            .placeholder(R.drawable.card_bg)
            .error(R.drawable.card_bg)
            .into(holder.img)

        holder.btnWatch.setOnClickListener {
            val i = Intent(context, PlayerActivity::class.java).apply {
                putExtra("EXTRA_URL", ch.url)
                putExtra("EXTRA_TITLE", ch.name)
            }
            context.startActivity(i)
        }
    }

    override fun getItemCount(): Int = items.size
}
