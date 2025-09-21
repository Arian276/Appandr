package com.bc.tvappvlc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bc.tvappvlc.ui.screens.HomeScreen
import com.bc.tvappvlc.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                // Navegación: muestra HomeScreen
                HomeScreen()
            }
        }
    }
}
