package com.barriletecosmicotv.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barriletecosmicotv.data.repository.StreamRepository
import com.barriletecosmicotv.model.Stream
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StreamViewModel @Inject constructor(
    private val streamRepository: StreamRepository
) : ViewModel() {
    
    private val _streams = MutableStateFlow<List<Stream>>(emptyList())
    val streams: StateFlow<List<Stream>> = _streams.asStateFlow()
    
    private val _featuredStreams = MutableStateFlow<List<Stream>>(emptyList())
    val featuredStreams: StateFlow<List<Stream>> = _featuredStreams.asStateFlow()
    
    private val _currentStream = MutableStateFlow<Stream?>(null)
    val currentStream: StateFlow<Stream?> = _currentStream.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _searchResults = MutableStateFlow<List<Stream>>(emptyList())
    val searchResults: StateFlow<List<Stream>> = _searchResults.asStateFlow()
    
    fun loadStreams() {
        viewModelScope.launch {
            _isLoading.value = true
            streamRepository.getStreams().collect { streamList ->
                _streams.value = streamList
                _isLoading.value = false
            }
        }
    }
    
    fun loadFeaturedStreams() {
        viewModelScope.launch {
            _isLoading.value = true
            streamRepository.getFeaturedStreams().collect { streamList ->
                _featuredStreams.value = streamList
                _isLoading.value = false
            }
        }
    }
    
    fun loadStreamById(streamId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            streamRepository.getStreamById(streamId).collect { stream ->
                _currentStream.value = stream
                _isLoading.value = false
            }
        }
    }
    
    fun searchStreams(query: String) {
        viewModelScope.launch {
            _isLoading.value = true
            streamRepository.searchStreams(query).collect { streamList ->
                _searchResults.value = streamList
                _isLoading.value = false
            }
        }
    }
    
    fun clearSearch() {
        _searchResults.value = emptyList()
    }
}