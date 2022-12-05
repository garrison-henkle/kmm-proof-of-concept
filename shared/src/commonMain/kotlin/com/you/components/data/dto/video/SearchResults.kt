package com.you.components.data.dto.video

@kotlinx.serialization.Serializable
data class SearchResults(
    val estimated_matches: Int,
    val results: List<Result>
)