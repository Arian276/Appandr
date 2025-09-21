package com.barriletecosmicotv.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.barriletecosmicotv.ui.theme.LiveGlow
import com.barriletecosmicotv.ui.theme.LiveIndicator
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun ViewerCount(
    streamId: String,
    modifier: Modifier = Modifier
) {
    // Simulamos un conteo de espectadores que cambia dinámicamente
    var viewerCount by remember { mutableStateOf(generateInitialViewerCount(streamId)) }
    var showAnimation by remember { mutableStateOf(false) }
    
    // Animación del conteo
    val scale by animateFloatAsState(
        targetValue = if (showAnimation) 1.2f else 1f,
        animationSpec = tween(200, easing = FastOutSlowInEasing),
        finishedListener = {
            if (showAnimation) {
                showAnimation = false
            }
        },
        label = "viewerScale"
    )
    
    // Efecto de pulso para el icono de live
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )
    
    // Actualizar el conteo cada 3-8 segundos (simula cambios reales)
    LaunchedEffect(streamId) {
        while (true) {
            delay(Random.nextLong(3000, 8000))
            val change = Random.nextInt(-5, 15) // Más probabilidad de subir
            viewerCount = maxOf(50, viewerCount + change) // Mínimo 50 espectadores
            showAnimation = true
        }
    }
    
    Box(
        modifier = modifier
            .background(
                Color.Black.copy(alpha = 0.7f),
                RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            // Indicador LIVE pulsante
            Icon(
                imageVector = Icons.Default.RemoveRedEye,
                contentDescription = "Espectadores",
                tint = LiveIndicator,
                modifier = Modifier
                    .size(12.dp)
                    .scale(pulseScale)
            )
            
            // Conteo de espectadores con animación
            Text(
                text = formatViewerCount(viewerCount),
                color = Color.White,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.scale(scale)
            )
            
            // Punto pulsante que indica transmisión en vivo
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .scale(pulseScale)
                    .background(
                        LiveGlow,
                        androidx.compose.foundation.shape.CircleShape
                    )
            )
        }
    }
}

// Genera un conteo inicial basado en el streamId para consistencia
private fun generateInitialViewerCount(streamId: String): Int {
    val hash = streamId.hashCode()
    return when {
        hash % 10 == 0 -> Random.nextInt(800, 1500) // Canales populares
        hash % 3 == 0 -> Random.nextInt(200, 800)   // Canales medianos
        else -> Random.nextInt(50, 300)             // Canales pequeños
    }
}

// Formatea el conteo para mostrar K cuando es necesario
private fun formatViewerCount(count: Int): String {
    return when {
        count >= 1000 -> "${(count / 100) / 10.0}K"
        else -> count.toString()
    }
}