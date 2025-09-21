package com.bc.tvappvlc.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bc.tvappvlc.model.Channel

@Composable
fun HomeScreen(
    channels: List<Channel>,
    onChannelClick: (Channel) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            items(channels) { channel ->
                ChannelCard(channel = channel, onClick = { onChannelClick(channel) })
            }
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
            .fillMaxSize()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Text(
            text = channel.name,
            style = ThemeTokens.titleLarge,
            modifier = Modifier.fillMaxSize()
        )
    }
}
