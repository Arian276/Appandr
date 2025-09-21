package com.bc.tvappvlc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.bc.tvappvlc.ui.HomeScreen
import com.bc.tvappvlc.ui.theme.TvAppVlcTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TvAppVlcTheme {
                HomeScreen()
            }
        }
    }
}
