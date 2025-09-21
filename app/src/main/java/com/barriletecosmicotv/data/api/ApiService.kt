package com.barriletecosmicotv.data.api

import com.barriletecosmicotv.model.Stream
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    
    @GET("streams")
    suspend fun getStreams(): Response<List<Stream>>
    
    @GET("streams/featured")
    suspend fun getFeaturedStreams(): Response<List<Stream>>
    
    @GET("streams/{id}")
    suspend fun getStreamById(@Path("id") streamId: String): Response<Stream>
    
    @GET("streams/search")
    suspend fun searchStreams(@Query("query") query: String): Response<List<Stream>>
    
    @GET("streams/category/{category}")
    suspend fun getStreamsByCategory(@Path("category") category: String): Response<List<Stream>>
}