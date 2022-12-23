package com.you.components.data.dto.twitter

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("url")
data class TweetUrl(
    @SerialName("url") val shortenedUrl: String,
    @SerialName("display_url") val displayUrl: String,
    @SerialName("expanded_url") val expandedUrl: String,
    override val indices: List<Int>,
) : TweetEntity()