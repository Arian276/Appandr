package com.arian.appandr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        // Usamos GridLayoutManager con 2 columnas
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        // Lista de canales de ejemplo (asegúrate que incluya la categoría)
        val channels = listOf(
            Channel("ESPN Premium", "Deportes", "url1"),
            Channel("TNT Sports", "Deportes", "url2"),
            Channel("DirecTV Sports", "Deportes", "url3"),
            Channel("DirecTV+", "Entretenimiento", "url4"),
            Channel("ESPN HD", "Deportes", "url5"),
            Channel("ESPN 2 HD", "Deportes", "url6")
        )

        recyclerView.adapter = ChannelAdapter(channels)
    }
}
