package com.bc.tvappvlc.model

// DTO que calza con tu JSON (ajusta nombres si tu JSON usa otros)
data class ChannelDto(
    val id: String? = null,
    val name: String? = null,
    val category: String? = null,
    val url: String? = null,
    val logo: String? = null,
    val resolution: String? = null,
    val viewer_count: Int? = null
)

// Mapear DTO -> dominio
fun ChannelDto.toDomain(): Channel = Channel(
    id = id ?: "",                    // evita NullPointer
    name = name ?: "",
    category = category,
    url = url ?: "",
    logo = logo,
    resolution = resolution,
    viewer_count = viewer_count
)

// Opcional: dominio -> DTO (si lo necesitás)
fun Channel.toDto(): ChannelDto = ChannelDto(
    id = id,
    name = name,
    category = category,
    url = url,
    logo = logo,
    resolution = resolution,
    viewer_count = viewer_count
)
