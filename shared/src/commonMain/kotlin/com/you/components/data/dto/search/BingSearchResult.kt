package com.you.components.data.dto.search

import kotlinx.serialization.Serializable

@Serializable
data class BingSearchResult(
    val dateLastCrawled: String,
    val displayUrl: String,
    val isFamilyFriendly: Boolean,
    val isNavigational: Boolean,
    val language: String,
    val name: String,
    val snippet: String?,
    val thumbnailUrl: String?,
    val url: String
)