package com.bc.tvappvlc.ui

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.bc.tvappvlc.PlayerActivity
import com.bc.tvappvlc.model.Channel

@Composable
fun HomeScreen(channels: List<Channel>) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Barrilete TV") }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(channels) { channel ->
                ChannelCard(channel = channel) {
                    val intent = Intent(context, PlayerActivity::class.java).apply {
                        putExtra(PlayerActivity.EXTRA_URL, channel.streamUrl)
                    }
                    context.startActivity(intent)
                }
            }
        }
    }
}

@Composable
fun ChannelCard(channel: Channel, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            AsyncImage(
                model = channel.logo,
                contentDescription = channel.name,
                modifier = Modifier
                    .size(96.dp)
                    .padding(end = 12.dp),
                contentScale = ContentScale.Fit
            )
            Column {
                Text(
                    text = channel.name,
                    style = MaterialTheme.typography.titleMedium
                )
                if (!channel.description.isNullOrEmpty()) {
                    Text(
                        text = channel.description,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}
