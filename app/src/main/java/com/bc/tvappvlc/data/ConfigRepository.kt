package com.bc.tvappvlc.data

import android.content.Context
import com.bc.tvappvlc.model.RemoteConfig
import com.bc.tvappvlc.net.ServiceLocator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader

class ConfigRepository(private val context: Context) {

    /** Descarga el config remoto. Devuelve null si hay error. */
    suspend fun fetchRemoteConfig(): RemoteConfig? = withContext(Dispatchers.IO) {
        runCatching { ServiceLocator.api.getConfig() }.getOrNull()
    }

    /** Fallback opcional: lee un channels.json empaquetado en /assets. */
    fun readBundledChannelsJson(): String? = runCatching {
        context.assets.open("channels.json").bufferedReader().use(BufferedReader::readText)
    }.getOrNull()
}