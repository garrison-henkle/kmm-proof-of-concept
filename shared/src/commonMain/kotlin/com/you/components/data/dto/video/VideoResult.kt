package com.you.components.data.dto.video

import kotlinx.serialization.Serializable

@Serializable
data class VideoResult(
    val count: Int,
    val offset: Int,
    val page: Int,
    val searchResults: SearchResults
)