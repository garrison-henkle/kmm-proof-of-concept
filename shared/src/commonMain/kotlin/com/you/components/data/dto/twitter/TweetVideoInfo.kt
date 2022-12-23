package com.you.components.data.dto.twitter

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TweetVideoInfo(
    @SerialName("duration_millis") val durationMs: Long,
    val variants: List<TweetVideoVariant>,
)