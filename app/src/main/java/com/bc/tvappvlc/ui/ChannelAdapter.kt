package com.arian.appandr

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton

class ChannelAdapter(private val channels: List<Channel>) : RecyclerView.Adapter<ChannelAdapter.ChannelViewHolder>() {

    class ChannelViewHolder(view: View) : RecyclerView.ViewHolder(view) {
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
            // Aquí puedes agregar la lógica para cuando se presione el botón
            // Por ejemplo: Toast.makeText(holder.itemView.context, "Viendo ${channel.name}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount() = channels.size
}
