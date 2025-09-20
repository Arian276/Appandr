package com.sc.tvappvlc.ui // Tu nuevo paquete está bien

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arian.appandr.R // ¡Y AQUÍ TAMBIÉN AÑADIMOS LA LÍNEA MÁGICA!
import com.google.android.material.button.MaterialButton
import com.sc.tvappvlc.model.Channel

class ChannelAdapter(private val channels: List<Channel>) : RecyclerView.Adapter<ChannelAdapter.ChannelViewHolder>() {

    class ChannelViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Usamos las referencias a R que ahora sí se pueden encontrar
        val channelName: TextView = view.findViewById(R.id.channelName)
        val channelCategory: TextView = view.findViewById(R.id.channelCategory)
        val watchNowButton: MaterialButton = view.findViewById(R.id.watchNowButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_channel, parent, false)
        return ChannelViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChannelViewHolder, position: Int) {
        val channel = channels[position]
        holder.channelName.text = channel.name
        holder.channelCategory.text = channel.category

        holder.watchNowButton.setOnClickListener {
            // Lógica del botón
        }
    }

    override fun getItemCount() = channels.size
}
