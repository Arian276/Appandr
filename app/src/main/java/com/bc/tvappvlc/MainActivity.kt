package com.bc.tvappvlc

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.bc.tvappvlc.ui.HomeScreen
import com.bc.tvappvlc.ui.initThemeTokensIfNeeded

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // ⬇️ Carga tokens.json y animations.json sólo una vez
        initThemeTokensIfNeeded(this)

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
