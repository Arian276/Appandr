package com.bc.tvappvlc.ui.model

data class Channel(
    val id: String,
    val name: String,
    val logo: String,
    val image: String,
    val tags: List<String>,
    val live: Boolean,
    val streamUrl: String
)
