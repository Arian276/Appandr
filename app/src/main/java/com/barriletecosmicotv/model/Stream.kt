package com.barriletecosmicotv.model

data class Stream(
    val id: String,
    val title: String,
    val description: String,
    val thumbnailUrl: String,
    val streamUrl: String,
    val category: String,
    val duration: Long,
    val isFeatured: Boolean,
    val viewCount: Int,
    val createdAt: String,
    val updatedAt: String
)