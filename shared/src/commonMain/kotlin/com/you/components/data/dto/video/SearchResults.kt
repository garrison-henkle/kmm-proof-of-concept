package com.you.components.data.dto.video

import kotlinx.serialization.Serializable

@Serializable
data class SearchResults(
    val estimated_matches: Int,
    val results: List<Result>
)