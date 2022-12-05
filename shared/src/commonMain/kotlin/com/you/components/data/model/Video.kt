package com.you.components.data.model

import com.you.components.utils.parseISO8601Duration
import com.you.components.utils.removeHitMarkers

data class Video(
    val title: String,
    val url: String,
    val displayUrl: String,
    val thumbnailUrl: String?,
    val thumbnailWidth: Int? = null,
    val thumbnailHeight: Int? = null,
    val provider: String,
    val videoLength: String? = null,
    val width: Int?,
    val height: Int?,
) {
    companion object {
        internal fun fromVideoResult(result: com.you.components.data.dto.video.Result): Video {
            val provider = result.publisher.first()
            return Video(
                title = result.name.removeHitMarkers(),
                url = result.contentUrl,
                displayUrl = result.hostPageDisplayUrl,
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
