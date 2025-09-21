package com.barriletecosmicotv.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NetworkCheck
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.barriletecosmicotv.components.AnimatedSoccerButton
import com.barriletecosmicotv.components.ViewerCount
import com.barriletecosmicotv.ui.theme.*
import com.barriletecosmicotv.viewmodel.StreamViewModel
import kotlinx.coroutines.delay

// Logos de canales deportivos argentinos (simulados)
private val channelLogos = mapOf(
    "tnt-sports-hd" to "https://via.placeholder.com/150x80/1976D2/FFFFFF?text=TNT+Sports",
    "espn-premium-hd" to "https://via.placeholder.com/150x80/FF1744/FFFFFF?text=ESPN+Premium",
    "directv-sport" to "https://via.placeholder.com/150x80/FF5722/FFFFFF?text=DirecTV+Sport",
    "directv-plus" to "https://via.placeholder.com/150x80/9C27B0/FFFFFF?text=DirecTV%2B",
    "espn-hd" to "https://via.placeholder.com/150x80/4CAF50/FFFFFF?text=ESPN+HD",
    "espn2-hd" to "https://via.placeholder.com/150x80/FF9800/FFFFFF?text=ESPN+2+HD",
    "espn3-hd" to "https://via.placeholder.com/150x80/2196F3/FFFFFF?text=ESPN+3+HD",
    "fox-sports-hd" to "https://via.placeholder.com/150x80/795548/FFFFFF?text=Fox+Sports"
)

// Datos simulados de streams
private val mockStreams = listOf(
    MockStream("tnt-sports-hd", "TNT Sports HD", "HD 1080p", "Deportes"),
    MockStream("espn-premium-hd", "ESPN Premium HD", "HD 1080p", "Deportes"),
    MockStream("directv-sport", "DirecTV Sport", "HD 720p", "Deportes"),
    MockStream("directv-plus", "DirecTV+", "HD 1080p", "Deportes"),
    MockStream("espn-hd", "ESPN HD", "HD 1080p", "Deportes"),
    MockStream("espn2-hd", "ESPN 2 HD", "HD 720p", "Deportes"),
    MockStream("espn3-hd", "ESPN 3 HD", "HD 1080p", "Deportes"),
    MockStream("fox-sports-hd", "Fox Sports HD", "HD 720p", "Deportes")
)

data class MockStream(
    val id: String,
    val title: String,
    val quality: String,
    val category: String
)

@Composable
fun HomeScreen(
    navController: NavController? = null,
    onStreamClick: ((String) -> Unit)? = null,
    viewModel: StreamViewModel = hiltViewModel()
) {
    var isLoading by remember { mutableStateOf(true) }
    
    // Simular carga
    LaunchedEffect(Unit) {
        delay(1500)
        isLoading = false
    }
    
    if (isLoading) {
        LoadingScreen()
    } else {
        MainContent(
            onStreamClick = { streamId ->
                onStreamClick?.invoke(streamId)
                navController?.navigate("stream/$streamId")
            }
        )
    }
}

@Composable
private fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Icono giratorio
            val infiniteTransition = rememberInfiniteTransition(label = "rotation")
            val rotation by infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = 360f,
                animationSpec = infiniteRepeatable(
                    animation = tween(2000, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                ),
                label = "rotation"
            )
            
            Icon(
                imageVector = Icons.Default.Tv,
                contentDescription = null,
                tint = CosmicPrimary,
                modifier = Modifier
                    .size(48.dp)
                    .graphicsLayer { rotationZ = rotation }
            )
            
            Text(
                text = "Cargando canales...",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun MainContent(
    onStreamClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Header argentino
        ArgentinaHeader()
        
        // Grid de canales
        LazyVerticalGrid(
            columns = GridCells.Adaptive(280.dp),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            itemsIndexed(mockStreams) { index, stream ->
                ChannelCard(
                    stream = stream,
                    animationDelay = (index * 100).coerceAtMost(500),
                    onWatchClick = { onStreamClick(stream.id) }
                )
            }
        }
    }
}

@Composable
private fun ArgentinaHeader() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Logo animado
                val infiniteTransition = rememberInfiniteTransition(label = "logoSpin")
                val rotation by infiniteTransition.animateFloat(
                    initialValue = 0f,
                    targetValue = 360f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(4000, easing = LinearEasing),
                        repeatMode = RepeatMode.Restart
                    ),
                    label = "logoRotation"
                )
                
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            Brush.linearGradient(
                                colors = listOf(CosmicPrimary, CosmicSecondary)
                            ),
                            RoundedCornerShape(12.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Tv,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .size(24.dp)
                            .graphicsLayer { rotationZ = rotation }
                    )
                }
                
                Column {
                    // Título con gradiente argentino
                    Text(
                        text = "Barrilete Cósmico",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = CosmicPrimary,
                        modifier = Modifier
                    )
                    
                    Text(
                        text = "Fútbol argentino • Pasión • En vivo",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            // Badge EN VIVO animado
            LiveBadge()
        }
    }
}

@Composable
private fun LiveBadge() {
    val infiniteTransition = rememberInfiniteTransition(label = "livePulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )
    
    Surface(
        modifier = Modifier
            .graphicsLayer { scaleX = pulseScale; scaleY = pulseScale },
        color = LiveIndicator.copy(alpha = 0.1f),
        shape = RoundedCornerShape(20.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, LiveIndicator.copy(alpha = 0.3f))
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = Icons.Default.NetworkCheck,
                contentDescription = null,
                tint = LiveIndicator,
                modifier = Modifier.size(12.dp)
            )
            
            Icon(
                imageVector = Icons.Default.AutoAwesome,
                contentDescription = null,
                tint = CosmicSecondary,
                modifier = Modifier.size(12.dp)
            )
            
            Text(
                text = "EN VIVO",
                color = LiveIndicator,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun ChannelCard(
    stream: MockStream,
    animationDelay: Int,
    onWatchClick: () -> Unit
) {
    var isHovered by remember { mutableStateOf(false) }
    
    // Animación de entrada
    val animationSpec = tween<Float>(
        durationMillis = 600,
        delayMillis = animationDelay,
        easing = FastOutSlowInEasing
    )
    
    val alpha by animateFloatAsState(
        targetValue = 1f,
        animationSpec = animationSpec,
        label = "alpha"
    )
    
    val translateY by animateFloatAsState(
        targetValue = 0f,
        animationSpec = animationSpec,
        label = "translateY"
    )
    
    // Animación hover
    val scale by animateFloatAsState(
        targetValue = if (isHovered) 1.02f else 1f,
        animationSpec = tween(300, easing = FastOutSlowInEasing),
        label = "hoverScale"
    )
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .graphicsLayer {
                this.alpha = alpha
                this.translationY = translateY * 50f
                this.scaleX = scale
                this.scaleY = scale
            },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isHovered) 12.dp else 4.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Header del canal
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = stream.title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isHovered) CosmicPrimary else MaterialTheme.colorScheme.onSurface
                    )
                    
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = stream.category,
                                fontSize = 10.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                        
                        Surface(
                            color = CosmicSecondary.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = stream.quality,
                                fontSize = 10.sp,
                                color = CosmicSecondary,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
                
                ViewerCount(streamId = stream.id)
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Logo del canal
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    model = channelLogos[stream.id],
                    contentDescription = stream.title,
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer {
                            scaleX = if (isHovered) 1.1f else 1f
                            scaleY = if (isHovered) 1.1f else 1f
                        },
                    contentScale = ContentScale.Fit
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Botón VER AHORA
            AnimatedSoccerButton(
                streamId = stream.id,
                onWatchClick = onWatchClick
            )
        }
    }
}