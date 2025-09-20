package com.bc.tvappvlc.ui

import android.content.Context
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bc.tvappvlc.ui.model.Channel
import org.json.JSONArray
import java.nio.charset.Charset

@Composable
fun HomeScreen(
    onOpenPlayer: (url: String, title: String) -> Unit
) {
    AppTheme {
        val ctx = LocalContext.current
        val t = ThemeTokens.tokens
        val channels by remember { mutableStateOf(loadChannels(ctx)) }

        Surface(
            color = Color(android.graphics.Color.parseColor(t.colors.background))
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // Header
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(t.layout.headerHeightDp.dp)
                        .background(Color(android.graphics.Color.parseColor(t.colors.appBarBg))),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = "Barrilete Cósmico TV",
                        color = Color(android.graphics.Color.parseColor(t.colors.textPrimary)),
                        style = MaterialTheme.typography.displayLarge,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }

                // Grid responsiva
                val cfg = LocalConfiguration.current
                val widthDp = cfg.screenWidthDp
                val heightDp = cfg.screenHeightDp
                val isLandscape = widthDp > heightDp
                val isTablet = widthDp >= 600
                val columns = when {
                    isTablet -> t.layout.grid.colsTablet
                    isLandscape -> t.layout.grid.colsLandscape
                    else -> t.layout.grid.colsPortrait
                }

                LazyVerticalGrid(
                    columns = GridCells.Fixed(columns),
                    contentPadding = PaddingValues(all = t.layout.grid.paddingDp.dp),
                    horizontalArrangement = Arrangement.spacedBy(t.layout.grid.gutterDp.dp),
                    verticalArrangement = Arrangement.spacedBy(t.layout.grid.gutterDp.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(channels, key = { it.id }) { ch ->
                        ChannelCard(
                            channel = ch,
                            onClick = { onOpenPlayer(ch.streamUrl, ch.name) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ChannelCard(
    channel: Channel,
    onClick: () -> Unit
) {
    val t = ThemeTokens.tokens
    val a = ThemeTokens.anims
    var pressed by remember { mutableStateOf(false) }
    val targetScale = if (pressed) a.cardPress.scaleTo else a.cardHoverLift.scaleTo
    val scale by animateFloatAsState(targetValue = targetScale, label = "cardScale")

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(ratioFromString(t.layout.card.aspectRatio))
            .clip(RoundedCornerShape(t.shape.cardRadiusDp.dp))
            .background(Color(android.graphics.Color.parseColor(t.colors.surface)))
            .clickable(
                onClick = onClick,
                onClickLabel = channel.name
            )
            .scale(scale)
    ) {
        val imageUrl = channel.image.ifBlank { channel.logo }
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = channel.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        if (channel.live) {
            val alphaPulse by animateFloatAsState(
                targetValue = a.liveBadgePulse.alphaTo,
                label = "livePulse"
            )
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(t.badgeLive.radiusDp.dp))
                    .background(Color(android.graphics.Color.parseColor(t.colors.liveBadgeBg)))
                    .alpha(alphaPulse)
            ) {
                Text(
                    text = t.badgeLive.text,
                    color = Color(android.graphics.Color.parseColor(t.colors.liveBadgeText)),
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(
                        horizontal = t.badgeLive.padHdp.dp,
                        vertical = t.badgeLive.padVdp.dp
                    )
                )
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .background(Color.Black.copy(alpha = 0.35f))
                .padding(8.dp)
        ) {
            Text(
                text = channel.name,
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                maxLines = t.layout.card.titleMaxLines
            )
        }
    }
}

private fun ratioFromString(aspect: String): Float {
    val parts = aspect.split(":")
    return if (parts.size == 2) {
        val w = parts[0].toFloatOrNull() ?: 16f
        val h = parts[1].toFloatOrNull() ?: 9f
        w / h
    } else 16f / 9f
}

private fun loadChannels(context: Context): List<Channel> {
    val json = context.assets.open("channels.json")
        .use { it.readBytes().toString(Charset.defaultCharset()) }
    val arr = JSONArray(json)
    val list = ArrayList<Channel>()
    for (i in 0 until arr.length()) {
        val o = arr.getJSONObject(i)
        val id = o.optString("id", "ch$i")
        val name = o.optString("name", "Canal $i")
        val logo = o.optString("logo", "")
        val image = o.optString("image", logo)
        val live = o.optBoolean("live", true)
        val tags = (o.optJSONArray("tags") ?: JSONArray()).let { ja ->
            (0 until ja.length()).map { idx -> ja.optString(idx, "") }.filter { it.isNotBlank() }
        }
        val url = o.optString("streamUrl", "")
        list.add(Channel(id, name, logo, image, tags, live, url))
    }
    return list
}
