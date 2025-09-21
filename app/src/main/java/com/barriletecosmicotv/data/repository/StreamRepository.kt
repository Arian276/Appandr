package com.barriletecosmicotv.data.repository

import com.barriletecosmicotv.data.api.ApiService
import com.barriletecosmicotv.model.Stream
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StreamRepository @Inject constructor(
    private val apiService: ApiService
) {
    
    fun getStreams(): Flow<List<Stream>> = flow {
        try {
            val response = apiService.getStreams()
            if (response.isSuccessful) {
                response.body()?.let { streams ->
                    emit(streams)
                } ?: emit(emptyList())
            } else {
                emit(emptyList())
            }
        } catch (e: Exception) {
            emit(emptyList())
        }
    }
    
    fun getFeaturedStreams(): Flow<List<Stream>> = flow {
        try {
            val response = apiService.getFeaturedStreams()
            if (response.isSuccessful) {
                response.body()?.let { streams ->
                    emit(streams)
                } ?: emit(emptyList())
            } else {
                emit(emptyList())
            }
        } catch (e: Exception) {
            emit(emptyList())
        }
    }
    
    fun getStreamById(streamId: String): Flow<Stream?> = flow {
        try {
            val response = apiService.getStreamById(streamId)
            if (response.isSuccessful) {
                emit(response.body())
            } else {
                emit(null)
            }
        } catch (e: Exception) {
            emit(null)
        }
    }
    
    fun searchStreams(query: String): Flow<List<Stream>> = flow {
        try {
            val response = apiService.searchStreams(query)
            if (response.isSuccessful) {
                response.body()?.let { streams ->
                    emit(streams)
                } ?: emit(emptyList())
            } else {
                emit(emptyList())
            }
        } catch (e: Exception) {
            emit(emptyList())
        }
    }
}