package com.bc.tvappvlc.model

// Añadimos el campo "category"
data class Channel(
    val name: String,
    val category: String,
    val url: String
)
