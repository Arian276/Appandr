package com.bc.tvappvlc.net

import com.bc.tvappvlc.model.RemoteConfig
import retrofit2.http.GET

interface ApiService {
    // Cambiá "config.json" si tu endpoint es otro
    @GET("config.json")
    suspend fun getConfig(): RemoteConfig
}