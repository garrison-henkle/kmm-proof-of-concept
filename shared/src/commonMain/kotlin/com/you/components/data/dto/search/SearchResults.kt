package com.you.components.data.dto.search

import kotlinx.serialization.Serializable

@Serializable
data class SearchResults(
    val mainline: Mainline,
)