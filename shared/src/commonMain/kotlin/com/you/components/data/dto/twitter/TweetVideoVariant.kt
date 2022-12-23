package com.you.components.data.dto.twitter

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TweetVideoVariant(
    val bitrate: Int? = null,
    @SerialName("content_type") val type: String,
    val url: String
)