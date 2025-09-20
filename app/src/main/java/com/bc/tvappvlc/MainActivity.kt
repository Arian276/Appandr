package com.sc.tvappvlc.ui // Corregimos el paquete para que coincida con la carpeta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arian.appandr.R // Importamos R desde el paquete original de la app
import com.sc.tvappvlc.model.Channel // Importamos la clase Channel desde su paquete

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        // Volvemos a poner la lista de canales que sí funciona
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
