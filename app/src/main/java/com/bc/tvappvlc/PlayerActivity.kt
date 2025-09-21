package com.bc.tvappvlc

import android.net.Uri
import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageButton
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

        videoLayout = findViewById(R.id.videoLayout)
        val btnPlayPause: ImageButton = findViewById(R.id.btnPlayPause)
        val btnFullscreen: ImageButton = findViewById(R.id.btnFullscreen)

        val args = ArrayList<String>().apply {
            add("--no-drop-late-frames")
            add("--no-skip-frames")
            add("--rtsp-tcp")
        }

        libVlc = LibVLC(this, args)
        mediaPlayer = MediaPlayer(libVlc)
        mediaPlayer.attachViews(videoLayout, null, false, false)

        val url = intent.getStringExtra(EXTRA_URL)
        if (!url.isNullOrEmpty()) {
            val media = Media(libVlc, Uri.parse(url))
            mediaPlayer.media = media
            media.release()
            mediaPlayer.play()
        }

        btnPlayPause.setOnClickListener {
            if (isPlaying) {
                mediaPlayer.pause()
            } else {
                mediaPlayer.play()
            }
            isPlaying = !isPlaying
        }

        btnFullscreen.setOnClickListener {
            isFullscreen = !isFullscreen
            // ⚡️ Aquí podés implementar tu lógica para fullscreen
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.stop()
        mediaPlayer.detachViews()
        libVlc.release()
    }
}}
