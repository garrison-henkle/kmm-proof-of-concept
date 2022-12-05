package com.you.components.data.dto.video

@kotlinx.serialization.Serializable
data class VideoResult(
    val count: Int,
    val offset: Int,
    val page: Int,
    val searchResults: SearchResults
)