package com.bc.tvappvlc

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import coil.load
import com.bc.tvappvlc.databinding.ActivityMainBinding
import com.bc.tvappvlc.model.Channel
import com.bc.tvappvlc.model.RemoteConfig
import com.bc.tvappvlc.net.ServiceLocator
import com.bc.tvappvlc.theme.ThemeManager
import com.bc.tvappvlc.ui.ChannelAdapter
import com.bc.tvappvlc.ui.GridSpacingItemDecoration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val channels = mutableListOf<Channel>()
    private lateinit var adapter: ChannelAdapter

    // Auto-refresh
    private val handler = Handler(Looper.getMainLooper())
    private val REFRESH_INTERVAL_MS = 15 * 60 * 1000L
    private val refreshTask = object : Runnable {
        override fun run() {
            loadConfig()
            handler.postDelayed(this, REFRESH_INTERVAL_MS)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recycler.layoutManager = GridLayoutManager(this, 2)
        adapter = ChannelAdapter(channels) { channel ->
            val i = Intent(this, PlayerActivity::class.java)
            i.putExtra(PlayerActivity.EXTRA_URL, channel.url)
            startActivity(i)
        }
        binding.recycler.adapter = adapter

        binding.swipeRefresh.setOnRefreshListener { loadConfig() }

        loadConfig()
    }

    override fun onStart() {
        super.onStart()
        handler.removeCallbacks(refreshTask)
        handler.postDelayed(refreshTask, REFRESH_INTERVAL_MS)
    }

    override fun onStop() {
        super.onStop()
        handler.removeCallbacks(refreshTask)
    }

    private fun loadConfig() {
        lifecycleScope.launch {
            val config: RemoteConfig? = withContext(Dispatchers.IO) {
                runCatching { ServiceLocator.api.getConfig() }.getOrNull()
            }

            if (config != null && config.channels.isNotEmpty()) {
                applyBrandingFromConfig(config)
                channels.clear()
                channels.addAll(config.channels)
                adapter.submitTheme(config)
                adapter.notifyDataSetChanged()
            } else {
                val local = readLocalChannelsFallback()
                channels.clear()
                channels.addAll(local)
                adapter.notifyDataSetChanged()
            }

            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun applyBrandingFromConfig(cfg: RemoteConfig) {
        // Título en toolbar
        val title = cfg.branding?.display_name ?: getString(R.string.app_name)
        this.title = title

        // Ajustar tamaño/espaciado del título si vienen del backend
        val toolbarTitleView = findViewTitleTextView()
        ThemeManager.applyToolbarTitleStyle(toolbarTitleView, cfg)

        // Colores
        val colors = ThemeManager.resolveColors(cfg, this)
        binding.root.setBackgroundColor(colors.background)

        // Header dinámico
        val header = findViewById<android.view.View>(R.id.headerBrand)
        val imgLogo = findViewById<ImageView>(R.id.imgLogoBrand)
        val txtTitle = findViewById<TextView>(R.id.txtBrandTitle)
        val txtSub = findViewById<TextView>(R.id.txtBrandSubtitle)
        val badge = findViewById<TextView>(R.id.badgeLive)

        cfg.branding?.logo_url?.let { url -> if (!url.isNullOrBlank()) imgLogo.load(url) }
        txtTitle.text = title
        txtTitle.setTextColor(colors.onSurface)
        txtSub.text = cfg.branding?.subtitle ?: ""
        txtSub.setTextColor(colors.muted)

        val showHeader = txtSub.text.isNotBlank() || (cfg.header?.show_live_badge == true)
        header.visibility = if (showHeader) android.view.View.VISIBLE else android.view.View.GONE

        if (cfg.header?.show_live_badge == true) {
            badge.visibility = android.view.View.VISIBLE
            badge.text = cfg.header.live_badge_text ?: "EN VIVO"
            badge.setTextColor(ThemeManager.color(cfg.header.live_fg, android.graphics.Color.WHITE))
            badge.setBackgroundColor(ThemeManager.color(cfg.header.live_bg, android.graphics.Color.RED))
        } else {
            badge.visibility = android.view.View.GONE
        }

        // Banner
        val bannerUrl = cfg.branding?.banner_url
        if (!bannerUrl.isNullOrBlank() && (cfg.layout?.home?.show_banner != false)) {
            binding.banner.load(bannerUrl)
            binding.banner.visibility = android.view.View.VISIBLE
        } else {
            binding.banner.visibility = android.view.View.GONE
        }

        // Grid: columnas + spacing
        val cols = cfg.layout?.grid_columns ?: 2
        (binding.recycler.layoutManager as? GridLayoutManager)?.spanCount = cols
        val spacingPx = ((cfg.layout?.grid_spacing_dp ?: 14) * resources.displayMetrics.density).toInt()
        while (binding.recycler.itemDecorationCount > 0) binding.recycler.removeItemDecorationAt(0)
        binding.recycler.addItemDecoration(GridSpacingItemDecoration(cols, spacingPx, true))
    }

    private fun findViewTitleTextView(): TextView? {
        val toolbar = binding.toolbar
        for (i in 0 until toolbar.childCount) {
            val v = toolbar.getChildAt(i)
            if (v is TextView) return v
        }
        return null
    }

    private fun readLocalChannelsFallback(): List<Channel> {
        return runCatching {
            val jsonText = assets.open("channels.json")
                .readBytes()
                .toString(Charset.forName("UTF-8"))
            val arr = JSONArray(jsonText)
            val out = mutableListOf<Channel>()
            for (i in 0 until arr.length()) {
                val o = arr.getJSONObject(i)
                out += Channel(
                    name = o.optString("name"),
                    logo = o.optString("logo"),
                    url = o.optString("url"),
                    category = o.optString("category", null),
                    resolution = o.optString("resolution", null),
                    viewer_count = o.optInt("viewer_count", 0)
                )
            }
            out
        }.getOrElse { emptyList() }
    }
}