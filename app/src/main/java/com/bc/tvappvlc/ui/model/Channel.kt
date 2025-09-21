package com.bc.tvappvlc.model

data class Channel(
    val name: String,
    val logo: String,
    val quality: String,
    val viewers: Int,
    val description: String,
    val cta: String,
    val url: String
)
