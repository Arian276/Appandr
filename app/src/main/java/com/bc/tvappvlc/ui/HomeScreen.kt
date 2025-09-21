package com.bc.tvappvlc.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.bc.tvappvlc.model.Channel

// Importamos los estilos centralizados
import com.bc.tvappvlc.ui.ThemeTokens

@Composable
fun HomeScreen(
    channels: List<Channel>,
    onChannelClick: (Channel) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(channels) { channel ->
            ChannelCard(channel = channel, onClick = { onChannelClick(channel) })
        }
    }
}

@Composable
fun ChannelCard(
    channel: Channel,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(channel.logo),
                contentDescription = channel.name,
                modifier = Modifier
                    .size(64.dp)
                    .padding(end = 8.dp),
                contentScale = ContentScale.Fit
            )

            Column {
                Text(
                    text = channel.name,
                    style = ThemeTokens.titleLarge // estilo título
                )
                Text(
                    text = channel.category ?: "Sin categoría",
                    style = ThemeTokens.bodyLarge // estilo cuerpo
                )
            }
        }
    }
}
