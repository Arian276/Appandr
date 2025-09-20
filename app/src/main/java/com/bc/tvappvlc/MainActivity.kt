package com.sc.tvappvlc.ui // Tu nuevo paquete está bien

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arian.appandr.R // ¡ESTA ES LA LÍNEA MÁGICA QUE LO ARREGLA!
import com.sc.tvappvlc.model.Channel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Usamos la referencia a R que acabamos de importar
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

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
