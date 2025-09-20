package com.bc.tvappvlc.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bc.tvappvlc.R
import com.bc.tvappvlc.model.Channel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        val channels = listOf(
            Channel(id = "espnp", name = "ESPN Premium", category = "Deportes", url = "url1"),
            Channel(id = "tnts",  name = "TNT Sports",   category = "Deportes", url = "url2"),
            Channel(id = "ds1",   name = "DirecTV Sports", category = "Deportes", url = "url3"),
            Channel(id = "dtv+",  name = "DirecTV+",     category = "Entretenimiento", url = "url4"),
            Channel(id = "espn",  name = "ESPN HD",      category = "Deportes", url = "url5"),
            Channel(id = "espn2", name = "ESPN 2 HD",    category = "Deportes", url = "url6")
        )

        recyclerView.adapter = ChannelAdapter(channels)
    }
}
