package com.bc.tvappvlc.model

data class Channel(
    val id: String = "",
    val name: String,
    val category: String? = null,
    val url: String = "",
    val logo: String? = null,
    val resolution: String? = null,
    val viewer_count: Int? = null
)

data class ChannelDto(
    val id: String? = null,
    val name: String? = null,
    val category: String? = null,
    val url: String? = null,
    val stream_url: String? = null,
    val logo: String? = null,
    val resolution: String? = null,
    val viewer_count: Any? = null
)

fun ChannelDto.toDomain(): Channel {
    val finalUrl = (stream_url ?: url ?: "").trim()
    val viewers: Int? = when (viewer_count) {
        is Int -> viewer_count
        is Long -> viewer_count.toInt()
        is String -> viewer_count.toIntOrNull()
        else -> null
    }
    return Channel(
        id = (id ?: "").trim(),
        name = (name ?: "").trim(),
        category = category,
        url = finalUrl,
        logo = logo,
        resolution = resolution,
        viewer_count = viewers
    )
}
