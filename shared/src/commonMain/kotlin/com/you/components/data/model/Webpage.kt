package com.you.components.data.model

import com.you.components.data.api.HasScalableThumbnail
import com.you.components.data.dto.search.BingSearchResult
import com.you.components.utils.removeHitMarkers
import kotlinx.serialization.Serializable

@Serializable
data class Webpage(
    val name: String,
    val snippet: String?,
    val url: String,
    val displayUrl: String,
    override val thumbnailUrl: String?,
    val safe: Boolean
) : HasScalableThumbnail() {
    override val thumbnailWidth: Int? = null
    override val thumbnailHeight: Int? = null

    companion object {
        internal fun fromSearchResult(result: BingSearchResult) = Webpage(
            name = result.name.removeHitMarkers(),
            snippet = result.snippet,
            url = result.url,
            displayUrl = result.displayUrl.removeHitMarkers(),
            thumbnailUrl = result.thumbnailUrl,
            safe = result.isFamilyFriendly,
        )
    }
}