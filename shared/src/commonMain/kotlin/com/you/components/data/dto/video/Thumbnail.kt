package com.you.components.data.dto.video

import kotlinx.serialization.Serializable

@Serializable
data class Thumbnail(
    val height: Int,
    val width: Int
)