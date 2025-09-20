package com.bc.tvappvlc.model

// Función de extensión que transforma ChannelDto → Channel (UI)
fun ChannelDto.toUiModel(): Channel {
    return Channel(
        name = this.name,
        logo = this.logo,
        url = this.stream_url,
        category = this.category,
        resolution = this.quality,
        viewer_count = this.viewers
    )
}