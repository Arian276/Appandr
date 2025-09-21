package com.bc.tvappvlc.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.bc.tvappvlc.ui.model.Channel

@Composable
fun HomeScreen() {
    val channels = listOf(
        Channel(
            name = "Prueba 1",
            logo = "https://i.imgur.com/9JrQOeM.png",
            url = "http://190.52.105.146:8000/play/a04m"
        ),
        Channel(
            name = "Prueba",
            logo = "https://i.imgur.com/3ZQ3Vw7.png",
            url = "http://190.52.105.146:8000/play/a0b9"
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Text(
            text = "Barrilete Cósmico TV",
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onBackground
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(channels) { channel ->
                ChannelCard(channel)
            }
        }
    }
}

@Composable
fun ChannelCard(channel: Channel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(channel.logo),
                contentDescription = channel.name,
                modifier = Modifier
                    .size(100.dp)
                    .padding(end = 12.dp),
                contentScale = ContentScale.Fit
            )
            Text(
                text = channel.name,
                style = TextStyle(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
        }
    }
}
