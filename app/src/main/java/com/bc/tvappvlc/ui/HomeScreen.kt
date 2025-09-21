package com.bc.tvappvlc.ui

import android.content.res.AssetManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bc.tvappvlc.R
import com.bc.tvappvlc.model.Channel
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader

class HomeScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_TVAppVLC) // asegúrate que existe
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // si tenés un layout principal; si no, crea uno simple con un RecyclerView con id rv

        // Si no tenés activity_main.xml, podés crear uno con solo un RecyclerView:
        // <androidx.recyclerview.widget.RecyclerView
        //     android:id="@+id/rv"
        //     android:layout_width="match_parent"
        //     android:layout_height="match_parent"
        //     android:padding="8dp"
        //     android:clipToPadding="false"/>

        val rv = findViewById<RecyclerView>(R.id.rv)
        rv.layoutManager = GridLayoutManager(this, 2)
        rv.setHasFixedSize(true)

        val channels = readChannelsFromAssets(assets)
        rv.adapter = ChannelAdapter(this, channels)
    }

    private fun readChannelsFromAssets(am: AssetManager): List<Channel> {
        val result = mutableListOf<Channel>()
        val sb = StringBuilder()
        am.open("channels.json").use { input ->
            BufferedReader(InputStreamReader(input)).useLines { lines ->
                lines.forEach { sb.append(it) }
            }
        }
        val arr = JSONArray(sb.toString())
        for (i in 0 until arr.length()) {
            val o = arr.getJSONObject(i)
            result.add(
                Channel(
                    name = o.optString("name"),
                    logo = o.optString("logo"),
                    url = o.optString("url"),
                    description = o.optString("description"),
                    quality = o.optString("quality", "1080p"),
                    category = o.optString("category", ""),
                    viewers = o.optInt("viewers", 0)
                )
            )
        }
        return result
    }
}
