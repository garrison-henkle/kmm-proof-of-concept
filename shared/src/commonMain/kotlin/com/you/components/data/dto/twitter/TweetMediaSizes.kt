package com.you.components.data.dto.twitter

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TweetMediaSizes(
    @SerialName("thumb") val thumbnail: TweetMediaSize,
    val small: TweetMediaSize,
    val medium: TweetMediaSize,
    val large: TweetMediaSize,
)