package com.bc.tvappvlc.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.bc.tvappvlc.PlayerActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            HomeScreen { url, title ->
                startActivity(Intent(this, PlayerActivity::class.java).apply {
                    putExtra("extra_url", url)
                    putExtra("extra_title", title)
                })
            }
        }
    }
}
