package com.you.components.data.dto.search

import kotlinx.serialization.Serializable

@Serializable
data class SearchResult(
    val count: Int,
    val page: Int,
    val searchResults: SearchResults
)