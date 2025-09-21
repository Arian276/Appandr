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

        // Carga canales desde assets/channels.json (con fallback url/streamUrl)
        val channels = loadChannelsFromAssets()

        setContent {
            AppTheme {
                HomeScreen(channels = channels)
            }
        }
    }

    private fun loadChannelsFromAssets(): List<Channel> {
        val list = mutableListOf<Channel>()
        val jsonStr = assets.open("channels.json")
            .use { it.readBytes().toString(Charset.defaultCharset()) }

        // El archivo puede ser un array raíz o un objeto con "channels"
        val root = jsonStr.trim()
        val array: JSONArray = when {
            root.startsWith("[") -> JSONArray(root)
            root.startsWith("{") -> {
                val obj = JSONObject(root)
                when {
                    obj.has("channels") -> obj.getJSONArray("channels")
                    else -> JSONArray() // vacío si no sigue el formato esperado
                }
            }
            else -> JSONArray()
        }

        for (i in 0 until array.length()) {
            val o = array.getJSONObject(i)

            val name = o.optString("name", "Canal ${i + 1}")
            val logo = o.optString("logo", "")
            // Soportar "streamUrl" o "url" en el JSON
            val streamUrl = when {
                o.has("streamUrl") -> o.optString("streamUrl", "")
                o.has("url") -> o.optString("url", "")
                else -> ""
            }
            val description = o.optString("description", "")

            // Crea el Channel según tu data class
            // Si tu Channel NO tiene 'description', borra ese argumento.
            list.add(
                Channel(
                    name = name,
                    logo = logo,
                    streamUrl = streamUrl,
                    description = if (description.isBlank()) null else description
                )
            )
        }
        return list
    }
}
