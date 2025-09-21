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
            .padding(Dimens.paddingMedium)
    ) {
        Text(
            text = "Barrilete Cósmico TV",
            style = TextStyle(
                fontFamily = defaultFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = TextSizes.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            ),
            modifier = Modifier.padding(bottom = Dimens.paddingMedium)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(Dimens.paddingSmall)
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
            .height(180.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(Dimens.cardRadius)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(Dimens.paddingMedium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(channel.logo),
                contentDescription = channel.name,
                modifier = Modifier
                    .size(120.dp)
                    .padding(end = Dimens.paddingMedium),
                contentScale = ContentScale.Fit
            )
            Text(
                text = channel.name,
                style = TextStyle(
                    fontFamily = defaultFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = TextSizes.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
        }
    }
}
