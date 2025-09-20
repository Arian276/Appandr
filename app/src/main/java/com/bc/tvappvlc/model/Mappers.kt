fun fromJson(dto: ChannelDto): Channel = Channel(
    id = dto.id,
    name = dto.name,
    url = dto.url,
    logo = dto.logo,
    resolution = dto.resolution,
    viewer_count = dto.viewer_count
)
