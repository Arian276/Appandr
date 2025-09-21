package com.bc.tvappvlc

import android.net.Uri
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import org.videolan.libvlc.LibVLC
import org.videolan.libvlc.Media
import org.videolan.libvlc.MediaPlayer
import org.videolan.libvlc.util.VLCVideoLayout

class PlayerActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_URL = "extra_url" // ✅ constante accesible desde HomeScreen
    }

    private lateinit var libVlc: LibVLC
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var videoLayout: VLCVideoLayout

    private var isPlaying = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        videoLayout = findViewById(R.id.videoLayout)

        // Inicializar VLC
        libVlc = LibVLC(this, ArrayList<String>().apply {
            add("--no-drop-late-frames")
            add("--no-skip-frames")
        })
        mediaPlayer = MediaPlayer(libVlc)
        mediaPlayer.attachViews(videoLayout, null, false, false)

        // Recuperar URL desde el intent
        val url = intent.getStringExtra(EXTRA_URL)
        if (url != null) {
            val media = Media(libVlc, Uri.parse(url))
            mediaPlayer.media = media
            media.release()
            mediaPlayer.play()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.stop()
        mediaPlayer.detachViews()
        mediaPlayer.release()
        libVlc.release()
    }
}
