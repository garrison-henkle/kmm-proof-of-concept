package com.you.components.data.model

import com.you.components.data.api.HasScalableThumbnail
import com.you.components.data.dto.video.Result
import com.you.components.utils.parseISO8601Duration
import com.you.components.utils.removeHitMarkers

import kotlinx.serialization.Serializable

@Serializable
data class Video(
    val title: String,
    val url: String,
    val displayUrl: String,
    override val thumbnailUrl: String?,
    override val thumbnailWidth: Int? = null,
    override val thumbnailHeight: Int? = null,
    val provider: String,
    val videoLength: String? = null,
    val width: Int?,
    val height: Int?,
) : HasScalableThumbnail() {
    companion object {
        internal fun fromVideoResult(result: Result): Video {
            val provider = result.publisher.first()
            return Video(
                title = result.name.removeHitMarkers(),
                url = result.contentUrl,
                displayUrl = result.hostPageDisplayUrl.removeHitMarkers(),
                thumbnailUrl = result.thumbnailUrl,
                thumbnailWidth = result.thumbnail?.width,
                thumbnailHeight = result.thumbnail?.height,
                provider = provider.name,
                videoLength = result.duration?.parseISO8601Duration(),
                width = result.width,
                height = result.height,
            )
        }
    }
}
