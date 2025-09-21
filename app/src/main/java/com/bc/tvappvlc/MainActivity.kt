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
        setContent {
            AppTheme {
                // TODO: reemplazar con datos cargados de JSON remoto
                val sampleChannels = listOf(
                    Channel(id = "1", name = "Canal Demo 1", streamUrl = "http://test/1"),
                    Channel(id = "2", name = "Canal Demo 2", streamUrl = "http://test/2")
                )

                HomeScreen(
                    channels = sampleChannels,
                    onChannelClick = { channel ->
                        // TODO: lanzar PlayerActivity con channel.streamUrl
                    }
                )
            }
        }
    }
}
