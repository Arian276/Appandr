package com.bc.tvappvlc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.bc.tvappvlc.model.Channel
import com.bc.tvappvlc.ui.HomeScreen
import com.bc.tvappvlc.ui.theme.AppTheme
import org.json.JSONArray
import org.json.JSONObject
import java.nio.charset.Charset

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val channels = loadChannelsFromAssets()

        setContent {
            AppTheme {
                HomeScreen(channels = channels)
            }
        }
    }

    /**
     * Lee app/src/main/assets/channels.json
     * Acepta formato:
     * [
     *   {"name":"...", "logo":"...", "url":"..."},
     *   ...
     * ]
     * y también mapea "streamUrl" -> "url" si viniera con ese nombre.
     */
    private fun loadChannelsFromAssets(): List<Channel> {
        val list = mutableListOf<Channel>()
        val jsonStr = assets.open("channels.json")
            .use { it.readBytes().toString(Charset.defaultCharset()) }
            .trim()

        val array: JSONArray = when {
            jsonStr.startsWith("[") -> JSONArray(jsonStr)
            jsonStr.startsWith("{") -> {
                val obj = JSONObject(jsonStr)
                when {
                    obj.has("channels") -> obj.getJSONArray("channels")
                    else -> JSONArray()
                }
            }
            else -> JSONArray()
        }

        for (i in 0 until array.length()) {
            val o = array.getJSONObject(i)

            val name = o.optString("name", "Canal ${i + 1}")
            val logo = o.optString("logo", "")
            // aceptamos "url" o "streamUrl" y lo guardamos en url
            val url = when {
                o.has("url") -> o.optString("url", "")
                o.has("streamUrl") -> o.optString("streamUrl", "")
                else -> ""
            }

            if (url.isNotBlank()) {
                list.add(Channel(name = name, logo = logo, url = url))
            }
        }
        return list
    }
}
