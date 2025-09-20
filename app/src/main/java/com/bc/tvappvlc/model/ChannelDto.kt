package com.bc.tvappvlc.model

// Este modelo debe reflejar 1:1 lo que devuelve tu backend en /channels
data class ChannelDto(
    val name: String,
    val logo: String,
    val stream_url: String,
    val category: String? = null,
    val quality: String? = null,
    val viewers: Int = 0
)