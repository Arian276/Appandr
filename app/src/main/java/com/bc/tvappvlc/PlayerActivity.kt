package com.bc.tvappvlc

import android.net.Uri
import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.videolan.libvlc.LibVLC
import org.videolan.libvlc.Media
import org.videolan.libvlc.MediaPlayer
import org.videolan.libvlc.util.VLCVideoLayout

class PlayerActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_URL = "extra_url"
    }

    private lateinit var libVlc: LibVLC
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var videoLayout: VLCVideoLayout

    private var isPlaying = true
    private var isFullscreen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        val url = intent.getStringExtra(EXTRA_URL)
        if (url.isNullOrBlank()) {
            finish()
            return
        }

        // Configuración de VLC
        libVlc = LibVLC(this, arrayListOf("--network-caching=2000", "--clock-jitter=0", "--clock-synchro=0"))
        mediaPlayer = MediaPlayer(libVlc)

        videoLayout = findViewById(R.id.video_surface)
        mediaPlayer.attachViews(videoLayout, null, false, false)

        val media = Media(libVlc, Uri.parse(url)).apply {
            setHWDecoderEnabled(true, true)
            addOption(":network-caching=2000")
        }
        mediaPlayer.media = media
        media.release()
        mediaPlayer.play()

        // --- Controles ---
        val btnPlayPause = findViewById<ImageButton>(R.id.btnPlayPause)
        val btnStop = findViewById<ImageButton>(R.id.btnStop)
        val btnFullscreen = findViewById<ImageButton>(R.id.btnFullscreen)
        val btnExit = findViewById<ImageButton>(R.id.btnExit)

        // Estado inicial: está reproduciendo
        btnPlayPause.setImageResource(R.drawable.ic_pause)

        btnPlayPause.setOnClickListener {
            if (isPlaying) {
                mediaPlayer.pause()
                btnPlayPause.setImageResource(R.drawable.ic_play)
            } else {
                mediaPlayer.play()
                btnPlayPause.setImageResource(R.drawable.ic_pause)
            }
            isPlaying = !isPlaying
        }

        btnStop.setOnClickListener {
            mediaPlayer.stop()
            btnPlayPause.setImageResource(R.drawable.ic_play)
            isPlaying = false
        }

        btnFullscreen.setOnClickListener {
            toggleFullscreen()
            val icon = if (isFullscreen) R.drawable.ic_fullscreen_exit else R.drawable.ic_fullscreen
            btnFullscreen.setImageResource(icon)
        }

        btnExit.setOnClickListener { finish() }

        // Manejo de error de reproducción
        mediaPlayer.setEventListener { event ->
            if (event?.type == MediaPlayer.Event.EncounteredError) {
                runOnUiThread {
                    Toast.makeText(this, "Error al reproducir el stream", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
    }

    private fun toggleFullscreen() {
        if (!isFullscreen) {
            supportActionBar?.hide()
            window.decorView.systemUiVisibility =
                (android.view.View.SYSTEM_UI_FLAG_FULLSCREEN
                        or android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or android.view.View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        } else {
            supportActionBar?.show()
            window.decorView.systemUiVisibility = android.view.View.SYSTEM_UI_FLAG_VISIBLE
        }
        isFullscreen = !isFullscreen
    }

    override fun onStop() {
        super.onStop()
        if (::mediaPlayer.isInitialized) mediaPlayer.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::mediaPlayer.isInitialized) {
            mediaPlayer.stop()
            mediaPlayer.detachViews()
            mediaPlayer.release()
        }
        if (::libVlc.isInitialized) libVlc.release()
        window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }
}