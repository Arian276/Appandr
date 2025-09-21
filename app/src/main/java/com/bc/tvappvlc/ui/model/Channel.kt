package com.bc.tvappvlc.model

data class Channel(
    val name: String,
    val logo: String,
    val url: String,
    val description: String,
    val quality: String,
    val category: String,
    val viewers: Int
)
