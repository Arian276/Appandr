package com.bc.tvappvlc.ui

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
for ChatGPT: this is 1. import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bc.tvappvlc.R
import com.bc.tvappvlc.model.Channel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader

@Composable
fun HomeScreen() {
    val context = LocalContext.current
    val channels = remember { mutableListOf<Channel>() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        val loaded = loadChannelsFromJson(context)
        channels.clear()
        channels.addAll(loaded)
    }

    // Contenedor principal
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            // Título de la pantalla
            Text(
                text = "Barrilete Cósmico",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Fútbol argentino · Pasión · En vivo",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Grilla de tarjetas de canales
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(channels) { channel ->
                    ChannelCard(channel = channel)
                }
            }
        }
    }
}

@Composable
private fun ChannelCard(channel: Channel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                // Aquí podrías abrir un navegador interno/external con el enlace channel.url
            },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            // Imagen del canal
            val imagePainter: Painter = painterResource(
                id = getImageIdByName(LocalContext.current, channel.logo)
            )
            Box(
                modifier = Modifier
                    .height(120.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = imagePainter,
                    contentDescription = channel.name,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Fila con calidad y número de espectadores
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = channel.quality,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "${channel.viewers} espectadores",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Nombre y descripción
            Text(
                text = channel.name,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = channel.description,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Botón CTA
            Button(
                onClick = {
                    // Podrías navegar a una pantalla de streaming o abrir un WebView con channel.url
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = channel.cta.uppercase())
            }
        }
    }
}

// Utilidad para cargar canales desde JSON en assets
suspend fun loadChannelsFromJson(context: Context): List<Channel> =
    withContext(Dispatchers.IO) {
        val inputStream = context.assets.open("channels.json")
        val jsonString = BufferedReader(InputStreamReader(inputStream)).use { it.readText() }
        val jsonArray = JSONArray(jsonString)
        val list = mutableListOf<Channel>()

        for (i in 0 until jsonArray.length()) {
            val obj = jsonArray.getJSONObject(i)
            list.add(
                Channel(
                    name = obj.getString("name"),
                    logo = obj.getString("logo"),
                    quality = obj.getString("quality"),
                    viewers = obj.getInt("viewers"),
                    description = obj.getString("description"),
                    cta = obj.getString("cta"),
                    url = obj.getString("url")
                )
            )
        }
        list
    }

// Utilidad para obtener id de recurso drawable por nombre
fun getImageIdByName(context: Context, imageName: String): Int {
    return context.resources.getIdentifier(
        imageName.substringBeforeLast('.'),
        "drawable",
        context.packageName
    )
}
