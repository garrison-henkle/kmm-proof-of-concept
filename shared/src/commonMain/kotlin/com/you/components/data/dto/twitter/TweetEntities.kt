package com.you.components.data.dto.twitter

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TweetEntities(
    val hashtags: List<TweetHashtag>,
    @SerialName("user_mentions") val mentions: List<TweetMention>,
    val urls: List<TweetUrl>
)