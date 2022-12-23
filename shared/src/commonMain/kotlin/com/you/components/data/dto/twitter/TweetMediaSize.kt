package com.you.components.data.dto.twitter

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TweetMediaSize(
    @SerialName("w") val width: Int,
    @SerialName("h") val height: Int
)