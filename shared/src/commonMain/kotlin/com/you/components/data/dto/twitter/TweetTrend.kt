package com.you.components.data.dto.twitter

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TweetTrend(
    val name: String,
    val query: String,
    @SerialName("tweet_volume") val tweetVolume: Int?
)