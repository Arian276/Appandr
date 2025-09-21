package com.bc.tvappvlc.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bc.tvappvlc.ui.components.ChannelCard
import com.bc.tvappvlc.ui.viewmodel.ChannelViewModel
import com.bc.tvappvlc.ui.viewmodel.ChannelViewModelFactory

@Composable
fun HomeScreen() {
    val viewModel: ChannelViewModel = viewModel(
        factory = ChannelViewModelFactory(LocalContext.current.applicationContext as android.app.Application)
    )
    val channels by viewModel.channels.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(padding)
        ) {
            if (channels.isEmpty()) {
                // Muestra un loader mientras carga
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                // Usamos LazyVerticalGrid para un layout de tarjetas al estilo web
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 160.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(channels) { channel ->
                        ChannelCard(
                            channel = channel,
                            onClick = { viewModel.incrementViews(channel.id) /* abre streaming */ }
                        )
                    }
                }
            }
        }
    }
}
