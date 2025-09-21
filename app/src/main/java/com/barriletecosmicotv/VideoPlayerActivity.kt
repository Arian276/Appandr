package com.barriletecosmicotv

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.barriletecosmicotv.components.VideoPlayer
import com.barriletecosmicotv.ui.theme.BarrileteCosmicTVTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VideoPlayerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val streamUrl = intent.getStringExtra("STREAM_URL") ?: ""
        
        setContent {
            BarrileteCosmicTVTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    VideoPlayer(
                        streamUrl = streamUrl,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}