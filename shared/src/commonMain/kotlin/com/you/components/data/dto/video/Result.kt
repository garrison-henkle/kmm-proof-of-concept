package com.you.components.data.dto.video

import kotlinx.serialization.Serializable

@Serializable
data class Result(
    val contentUrl: String,
    val datePublished: String,
    val description: String?,
    val duration: String?,
    val height: Int,
    val hostPageDisplayUrl: String,
    val hostPageUrl: String,
    val name: String,
    val publisher: List<Publisher>,
    val thumbnail: Thumbnail?,
    val thumbnailUrl: String?,
    val viewCount: Int?,
    val width: Int
)