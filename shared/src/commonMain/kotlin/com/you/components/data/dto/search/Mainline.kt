package com.you.components.data.dto.search

import kotlinx.serialization.Serializable

@Serializable
data class Mainline(
    val bing_market: String,
    val bing_search_results: List<BingSearchResult>,
    val estimated_matches: Int,
)