package com.bc.tvappvlc.model

data class Channel(
    val id: String? = null,
    val name: String,
    val logo: String? = null,
    val url: String,
    val category: String? = null,
    val resolution: String? = null,   // 👈 usado en ChannelAdapter
    val viewer_count: Int = 0         // 👈 usado en ChannelAdapter
)