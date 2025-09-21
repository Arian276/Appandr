package com.bc.tvappvlc.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Channel(
    val id: Int,
    val name: String,
    val description: String,
    val quality: String,
    val views: Int,
    val category: String,
    val logoUrl: String,
    val url: String
)
