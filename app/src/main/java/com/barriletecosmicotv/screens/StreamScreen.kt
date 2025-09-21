package com.barriletecosmicotv.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.barriletecosmicotv.components.VideoPlayer
import com.barriletecosmicotv.components.ViewerCount
import com.barriletecosmicotv.model.Stream
import com.barriletecosmicotv.ui.theme.*
import com.barriletecosmicotv.viewmodel.StreamViewModel
import kotlinx.coroutines.delay
import kotlin.random.Random

// Datos simulados para el chat
data class ChatMessage(
    val id: String,
    val username: String,
    val message: String,
    val timestamp: Long,
    val isSystem: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StreamScreen(
    streamId: String,
    navController: NavController? = null,
    onBackClick: (() -> Unit)? = null,
    viewModel: StreamViewModel = hiltViewModel()
) {
    var currentStream by remember { mutableStateOf<Stream?>(null) }
    val stream by viewModel.currentStream.collectAsState()
    var showDonationModal by remember { mutableStateOf(false) }
    var likeCount by remember { mutableStateOf(Random.nextInt(50, 500)) }
    var hasLiked by remember { mutableStateOf(false) }
    var isFullscreen by remember { mutableStateOf(false) }
    
    LaunchedEffect(streamId) {
        viewModel.loadStreamById(streamId)
        delay(3000) // Simular carga y luego mostrar modal
        showDonationModal = true
    }
    
    LaunchedEffect(stream) {
        currentStream = stream
    }
    
    Box(modifier = Modifier.fillMaxSize()) {
        if (currentStream == null) {
            // Pantalla de carga
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    val infiniteTransition = rememberInfiniteTransition(label = "loading")
                    val scale by infiniteTransition.animateFloat(
                        initialValue = 1f,
                        targetValue = 1.2f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(800, easing = FastOutSlowInEasing),
                            repeatMode = RepeatMode.Reverse
                        ),
                        label = "logoScale"
                    )
                    
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .graphicsLayer { 
                                scaleX = scale
                                scaleY = scale
                            }
                            .background(
                                Brush.linearGradient(
                                    colors = listOf(CosmicPrimary, CosmicSecondary)
                                ),
                                CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "BC",
                            color = Color.White,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    
                    Text(
                        text = "Barrilete Cósmico",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = CosmicPrimary,
                        textAlign = TextAlign.Center
                    )
                    
                    Text(
                        text = "Cargando transmisión...",
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 14.sp
                    )
                }
            }
        } else {
            currentStream?.let { stream ->
                if (isFullscreen) {
                    // Pantalla completa
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black),
                        contentAlignment = Alignment.Center
                    ) {
                        VideoPlayer(
                            streamUrl = stream.streamUrl,
                            modifier = Modifier.fillMaxSize()
                        )
                        
                        IconButton(
                            onClick = { isFullscreen = false },
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(16.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.FullscreenExit,
                                contentDescription = "Salir de pantalla completa",
                                tint = Color.White
                            )
                        }
                    }
                } else {
                    // Contenido principal
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background)
                    ) {
                        // Header con navegación y likes
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    IconButton(
                                        onClick = {
                                            onBackClick?.invoke()
                                            navController?.popBackStack()
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.ArrowBack,
                                            contentDescription = "Volver",
                                            tint = MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                    
                                    Column {
                                        Text(
                                            text = stream.title,
                                            fontSize = 20.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        Text(
                                            text = "En vivo • HD 1080p",
                                            fontSize = 12.sp,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                                
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    ViewerCount(streamId = streamId)
                                    
                                    // Botón de likes
                                    Surface(
                                        onClick = {
                                            if (!hasLiked) {
                                                hasLiked = true
                                                likeCount += 1
                                            }
                                        },
                                        color = if (hasLiked) ArgentinaPassion.copy(alpha = 0.1f) else Color.Transparent,
                                        shape = RoundedCornerShape(20.dp),
                                        border = androidx.compose.foundation.BorderStroke(
                                            1.dp,
                                            if (hasLiked) ArgentinaPassion else MaterialTheme.colorScheme.outline
                                        )
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                                        ) {
                                            Icon(
                                                imageVector = if (hasLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                                contentDescription = "Like",
                                                tint = if (hasLiked) ArgentinaPassion else MaterialTheme.colorScheme.onSurface,
                                                modifier = Modifier.size(16.dp)
                                            )
                                            Text(
                                                text = likeCount.toString(),
                                                fontSize = 12.sp,
                                                color = if (hasLiked) ArgentinaPassion else MaterialTheme.colorScheme.onSurface
                                            )
                                        }
                                    }
                                }
                            }
                        }
                        
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            // Reproductor de video (70% del ancho)
                            Column(
                                modifier = Modifier.weight(0.7f)
                            ) {
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .aspectRatio(16f / 9f),
                                    colors = CardDefaults.cardColors(containerColor = Color.Black)
                                ) {
                                    Box(
                                        modifier = Modifier.fillMaxSize()
                                    ) {
                                        VideoPlayer(
                                            streamUrl = stream.streamUrl,
                                            modifier = Modifier.fillMaxSize()
                                        )
                                        
                                        // Botón pantalla completa
                                        IconButton(
                                            onClick = { isFullscreen = true },
                                            modifier = Modifier
                                                .align(Alignment.TopEnd)
                                                .padding(16.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Fullscreen,
                                                contentDescription = "Pantalla completa",
                                                tint = Color.White
                                            )
                                        }
                                        
                                        // Indicador de calidad
                                        Box(
                                            modifier = Modifier
                                                .align(Alignment.TopStart)
                                                .padding(16.dp)
                                                .background(
                                                    Color.Black.copy(alpha = 0.7f),
                                                    RoundedCornerShape(8.dp)
                                                )
                                                .padding(horizontal = 8.dp, vertical = 4.dp)
                                        ) {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.NetworkCheck,
                                                    contentDescription = null,
                                                    tint = LiveIndicator,
                                                    modifier = Modifier.size(12.dp)
                                                )
                                                Text(
                                                    text = "HD 1080p",
                                                    color = Color.White,
                                                    fontSize = 10.sp
                                                )
                                            }
                                        }
                                    }
                                }
                                
                                Spacer(modifier = Modifier.height(16.dp))
                                
                                // Información del stream
                                Card(
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Column(
                                        modifier = Modifier.padding(16.dp),
                                        verticalArrangement = Arrangement.spacedBy(12.dp)
                                    ) {
                                        Text(
                                            text = stream.title,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        
                                        Text(
                                            text = stream.description,
                                            fontSize = 14.sp,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            lineHeight = 20.sp
                                        )
                                        
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.NetworkCheck,
                                                contentDescription = null,
                                                tint = LiveIndicator,
                                                modifier = Modifier.size(16.dp)
                                            )
                                            Text(
                                                text = "Señal estable • Baja latencia • HD",
                                                fontSize = 12.sp,
                                                color = LiveIndicator
                                            )
                                        }
                                    }
                                }
                            }
                            
                            // Chat (30% del ancho)
                            ChatSectionForStream(
                                streamId = streamId,
                                modifier = Modifier.weight(0.3f)
                            )
                        }
                    }
                }
            }
        }
        
        // Modal de donación
        if (showDonationModal) {
            DonationModal(
                onDismiss = { showDonationModal = false }
            )
        }
    }
}

@Composable
private fun StreamLoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Animación de carga del logo
            val infiniteTransition = rememberInfiniteTransition(label = "loading")
            val scale by infiniteTransition.animateFloat(
                initialValue = 1f,
                targetValue = 1.2f,
                animationSpec = infiniteRepeatable(
                    animation = tween(800, easing = FastOutSlowInEasing),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "logoScale"
            )
            
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .graphicsLayer { 
                        scaleX = scale
                        scaleY = scale
                    }
                    .background(
                        Brush.linearGradient(
                            colors = listOf(CosmicPrimary, CosmicSecondary)
                        ),
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "BC",
                    color = Color.White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            // Título animado
            Text(
                text = "Barrilete Cósmico",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = CosmicPrimary,
                textAlign = TextAlign.Center
            )
            
            Text(
                text = "Cargando transmisión...",
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
private fun MainStreamContent(
    streamId: String,
    likeCount: Int,
    hasLiked: Boolean,
    isFullscreen: Boolean,
    onLikeClick: () -> Unit,
    onFullscreenToggle: () -> Unit,
    onBackClick: () -> Unit
) {
    if (isFullscreen) {
        FullscreenVideoPlayer(
            streamId = streamId,
            onExitFullscreen = onFullscreenToggle
        )
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Header del stream
            StreamHeader(
                streamId = streamId,
                likeCount = likeCount,
                hasLiked = hasLiked,
                onBackClick = onBackClick,
                onLikeClick = onLikeClick
            )
            
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Reproductor de video (70% del ancho)
                Column(
                    modifier = Modifier.weight(0.7f)
                ) {
                    VideoPlayerSection(
                        streamId = streamId,
                        onFullscreenClick = onFullscreenToggle
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Información del stream
                    StreamInfoSection(streamId = streamId)
                }
                
                // Chat (30% del ancho)
                ChatSection(
                    streamId = streamId,
                    modifier = Modifier.weight(0.3f)
                )
            }
        }
    }
}

@Composable
private fun StreamHeader(
    streamId: String,
    likeCount: Int,
    hasLiked: Boolean,
    onBackClick: () -> Unit,
    onLikeClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Volver",
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
                
                Column {
                    Text(
                        text = getStreamTitle(streamId),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "En vivo • HD 1080p",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ViewerCount(streamId = streamId)
                
                // Botón de likes
                Surface(
                    onClick = onLikeClick,
                    color = if (hasLiked) ArgentinaPassion.copy(alpha = 0.1f) else Color.Transparent,
                    shape = RoundedCornerShape(20.dp),
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        if (hasLiked) ArgentinaPassion else MaterialTheme.colorScheme.outline
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = if (hasLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Like",
                            tint = if (hasLiked) ArgentinaPassion else MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = likeCount.toString(),
                            fontSize = 12.sp,
                            color = if (hasLiked) ArgentinaPassion else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun VideoPlayerSection(
    streamId: String,
    onFullscreenClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f),
        colors = CardDefaults.cardColors(containerColor = Color.Black)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            // Placeholder del reproductor (aquí iría el reproductor real)
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Reproducir",
                    tint = Color.White,
                    modifier = Modifier.size(64.dp)
                )
                Text(
                    text = "Transmisión en vivo",
                    color = Color.White,
                    fontSize = 16.sp
                )
                Text(
                    text = getStreamTitle(streamId),
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 12.sp
                )
            }
            
            // Controles del reproductor
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
            ) {
                IconButton(onClick = onFullscreenClick) {
                    Icon(
                        imageVector = Icons.Default.Fullscreen,
                        contentDescription = "Pantalla completa",
                        tint = Color.White
                    )
                }
            }
            
            // Indicador de calidad
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(16.dp)
                    .background(
                        Color.Black.copy(alpha = 0.7f),
                        RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.NetworkCheck,
                        contentDescription = null,
                        tint = LiveIndicator,
                        modifier = Modifier.size(12.dp)
                    )
                    Text(
                        text = "HD 1080p",
                        color = Color.White,
                        fontSize = 10.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun StreamInfoSection(streamId: String) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Información del canal",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            
            Text(
                text = "Transmisión de deportes con calidad profesional. " +
                      "Disfruta del mejor fútbol argentino en vivo las 24 horas.",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 20.sp
            )
            
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.NetworkCheck,
                    contentDescription = null,
                    tint = LiveIndicator,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = "Señal estable • Baja latencia • HD",
                    fontSize = 12.sp,
                    color = LiveIndicator
                )
            }
        }
    }
}

@Composable
private fun ChatSection(
    streamId: String,
    modifier: Modifier = Modifier
) {
    var chatMessages by remember { mutableStateOf(generateMockChatMessages()) }
    val listState = rememberLazyListState()
    
    // Simular mensajes nuevos
    LaunchedEffect(streamId) {
        while (true) {
            delay(Random.nextLong(2000, 8000))
            val newMessage = generateRandomMessage()
            chatMessages = chatMessages + newMessage
            // Auto-scroll a los mensajes más recientes
            listState.animateScrollToItem(chatMessages.size - 1)
        }
    }
    
    Card(
        modifier = modifier.fillMaxHeight(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.fillMaxHeight()
        ) {
            // Header del chat
            Surface(
                color = CosmicPrimary.copy(alpha = 0.1f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Chat,
                        contentDescription = null,
                        tint = CosmicPrimary,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "Chat en vivo",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = CosmicPrimary
                    )
                    Text(
                        text = "${chatMessages.size}",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            // Mensajes del chat
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(chatMessages) { message ->
                    ChatMessageItem(message = message)
                }
            }
            
            // Input del chat (placeholder)
            Surface(
                color = MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Escribe un mensaje...",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.weight(1f)
                    )
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Enviar",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun ChatMessageItem(message: ChatMessage) {
    if (message.isSystem) {
        Text(
            text = message.message,
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    } else {
        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = message.username,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = CosmicPrimary
            )
            Text(
                text = message.message,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface,
                lineHeight = 16.sp
            )
        }
    }
}

@Composable
private fun FullscreenVideoPlayer(
    streamId: String,
    onExitFullscreen: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        // Reproductor a pantalla completa
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Reproducir",
                tint = Color.White,
                modifier = Modifier.size(96.dp)
            )
            Text(
                text = "Pantalla completa",
                color = Color.White,
                fontSize = 20.sp
            )
            Text(
                text = getStreamTitle(streamId),
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 14.sp
            )
        }
        
        // Botón para salir de pantalla completa
        IconButton(
            onClick = onExitFullscreen,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.FullscreenExit,
                contentDescription = "Salir de pantalla completa",
                tint = Color.White
            )
        }
    }
}

@Composable
private fun DonationModal(
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.6f))
            .clickable { onDismiss() },
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .wrapContentHeight()
                .clickable { }, // Evita que el click se propague
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(onClick = onDismiss) {
                        Text(
                            text = "❌",
                            fontSize = 18.sp
                        )
                    }
                }
                
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "❤️",
                        fontSize = 20.sp
                    )
                    Text(
                        text = "Apoyá el proyecto",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
                
                Text(
                    text = "Alias: barriletecosmicoTv",
                    fontSize = 14.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Medium
                )
                
                // Logos de canales
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        color = Color(0xFF1976D2),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.size(60.dp, 30.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = "TNT",
                                color = Color.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    
                    Surface(
                        color = Color(0xFFFF1744),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.size(60.dp, 30.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = "ESPN",
                                color = Color.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

// Funciones auxiliares
private fun getStreamTitle(streamId: String): String {
    return when (streamId) {
        "tnt-sports-hd" -> "TNT Sports HD"
        "espn-premium-hd" -> "ESPN Premium HD"
        "directv-sport" -> "DirecTV Sport"
        "directv-plus" -> "DirecTV+"
        "espn-hd" -> "ESPN HD"
        "espn2-hd" -> "ESPN 2 HD"
        "espn3-hd" -> "ESPN 3 HD"
        "fox-sports-hd" -> "Fox Sports HD"
        else -> "Canal Deportivo"
    }
}

private fun generateMockChatMessages(): List<ChatMessage> {
    val usernames = listOf("Futbolero2024", "Messi10", "BocaJuniors", "River_Fan", "Argentina", "Maradona_Legend")
    val messages = listOf(
        "¡Qué golazo!",
        "Vamos Argentina! 🇦🇷",
        "Este partido está increíble",
        "¿Vieron esa jugada?",
        "El mejor canal de deportes",
        "Dale campeón!",
        "Barrilete cósmico de Messi ⚽",
        "¡Qué calidad de imagen!",
        "Gracias por la transmisión"
    )
    
    return List(20) { index ->
        ChatMessage(
            id = "msg_$index",
            username = usernames.random(),
            message = messages.random(),
            timestamp = System.currentTimeMillis() - (index * 30000)
        )
    }
}

@Composable
private fun ChatSectionForStream(
    streamId: String,
    modifier: Modifier = Modifier
) {
    var chatMessages by remember { mutableStateOf(generateMockChatMessages()) }
    val listState = rememberLazyListState()
    
    // Simular mensajes nuevos
    LaunchedEffect(streamId) {
        while (true) {
            delay(Random.nextLong(2000, 8000))
            val newMessage = generateRandomMessage()
            chatMessages = chatMessages + newMessage
            // Auto-scroll a los mensajes más recientes
            listState.animateScrollToItem(chatMessages.size - 1)
        }
    }
    
    Card(
        modifier = modifier.fillMaxHeight(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.fillMaxHeight()
        ) {
            // Header del chat
            Surface(
                color = CosmicPrimary.copy(alpha = 0.1f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Chat,
                        contentDescription = null,
                        tint = CosmicPrimary,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "Chat en vivo",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = CosmicPrimary
                    )
                    Text(
                        text = "${chatMessages.size}",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            // Mensajes del chat
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(chatMessages) { message ->
                    ChatMessageItem(message = message)
                }
            }
            
            // Input del chat (placeholder)
            Surface(
                color = MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Escribe un mensaje...",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.weight(1f)
                    )
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Enviar",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun ChatMessageItem(message: ChatMessage) {
    if (message.isSystem) {
        Text(
            text = message.message,
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    } else {
        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = message.username,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = CosmicPrimary
            )
            Text(
                text = message.message,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface,
                lineHeight = 16.sp
            )
        }
    }
}

@Composable
private fun DonationModal(
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.6f))
            .clickable { onDismiss() },
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .wrapContentHeight()
                .clickable { }, // Evita que el click se propague
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(onClick = onDismiss) {
                        Text(
                            text = "❌",
                            fontSize = 18.sp
                        )
                    }
                }
                
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "❤️",
                        fontSize = 20.sp
                    )
                    Text(
                        text = "Apoyá el proyecto",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
                
                Text(
                    text = "Alias: barriletecosmicoTv",
                    fontSize = 14.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Medium
                )
                
                // Logos de canales
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        color = Color(0xFF1976D2),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.size(60.dp, 30.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = "TNT",
                                color = Color.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    
                    Surface(
                        color = Color(0xFFFF1744),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.size(60.dp, 30.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = "ESPN",
                                color = Color.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun generateMockChatMessages(): List<ChatMessage> {
    val usernames = listOf("Futbolero2024", "Messi10", "BocaJuniors", "River_Fan", "Argentina", "Maradona_Legend")
    val messages = listOf(
        "¡Qué golazo!",
        "Vamos Argentina! 🇦🇷",
        "Este partido está increíble",
        "¿Vieron esa jugada?",
        "El mejor canal de deportes",
        "Dale campeón!",
        "Barrilete cósmico de Messi ⚽",
        "¡Qué calidad de imagen!",
        "Gracias por la transmisión"
    )
    
    return List(20) { index ->
        ChatMessage(
            id = "msg_$index",
            username = usernames.random(),
            message = messages.random(),
            timestamp = System.currentTimeMillis() - (index * 30000)
        )
    }
}

private fun generateRandomMessage(): ChatMessage {
    val usernames = listOf("Futbolero2024", "Messi10", "BocaJuniors", "River_Fan", "Argentina", "Maradona_Legend")
    val messages = listOf(
        "¡Qué golazo!",
        "Vamos Argentina! 🇦🇷",
        "Este partido está increíble",
        "¿Vieron esa jugada?",
        "El mejor canal de deportes",
        "Dale campeón!",
        "Barrilete cósmico de Messi ⚽",
        "¡Qué calidad de imagen!",
        "Gracias por la transmisión"
    )
    
    return ChatMessage(
        id = "msg_${System.currentTimeMillis()}",
        username = usernames.random(),
        message = messages.random(),
        timestamp = System.currentTimeMillis()
    )
}