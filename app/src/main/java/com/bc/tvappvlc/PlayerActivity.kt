package com.bc.tvappvlc

import android.os.Bundle
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.setPadding
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

class PlayerActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_URL = "EXTRA_URL"
    }

    private var player: ExoPlayer? = null
    private lateinit var playerView: PlayerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Mantener pantalla encendida mientras se reproduce
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        // Crear PlayerView programáticamente (sin XML)
        playerView = PlayerView(this).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            setPadding(0)
            keepScreenOn = true
        }
        setContentView(playerView)

        // Obtener URL del intent
        val url = intent.getStringExtra(EXTRA_URL).orEmpty()
        if (url.isNotBlank()) {
            initPlayer(url)
        }
    }

    private fun initPlayer(url: String) {
        player = ExoPlayer.Builder(this).build().also { exo ->
            playerView.player = exo
            val mediaItem = MediaItem.fromUri(url)
            exo.setMediaItem(mediaItem)
            exo.prepare()
            exo.playWhenReady = true
        }
    }

    override fun onStart() {
        super.onStart()
        // Si quisieras re-crear el player al volver del background, podrías hacerlo acá.
    }

    override fun onStop() {
        super.onStop()
        // Liberar el player para evitar fugas
        playerView.player = null
        player?.release()
        player = null
    }
}
