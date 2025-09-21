package com.bc.tvappvlc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.bc.tvappvlc.model.Channel
import com.bc.tvappvlc.ui.HomeScreen
import com.bc.tvappvlc.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Datos dummy para compilar. Luego los reemplazás leyendo assets/channels.json
        val sampleChannels = listOf(
            Channel("Prueba 1", "https://i.imgur.com/9JrQOeM.png", "http://190.52.105.146:8000/play/a04m"),
            Channel("Prueba",   "https://i.imgur.com/3ZQ3Vw7.png", "http://190.52.105.146:8000/play/a0b9")
        )

        setContent {
            AppTheme {
                HomeScreen(
                    channels = sampleChannels,
                    onChannelClick = { /* TODO: lanzar PlayerActivity con it.url */ }
                )
            }
        }
    }
}
