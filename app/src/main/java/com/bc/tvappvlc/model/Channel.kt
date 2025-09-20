package com.bc.tvappvlc.model

data class Channel(
    val id: String,
    val name: String,
    val category: String? = null,
    val url: String,
    val logo: String? = null,
    val resolution: String? = null,
    val viewer_count: Int? = null
)
