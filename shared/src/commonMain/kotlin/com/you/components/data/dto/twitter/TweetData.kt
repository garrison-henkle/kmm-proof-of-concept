package com.you.components.data.dto.twitter

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TweetData(
    val id: String,
    @SerialName("full_text") val text: String,
    @SerialName("favorite_count") val likes: Int,
    @SerialName("retweet_count") val retweets: Int,
    @SerialName("created_at") val timestamp: String,
    val entities: TweetEntities,
    @SerialName("extended_entities") val extendedEntities: TweetExtendedEntities? = null,
    val user: TweetUser,
    @SerialName("possibly_sensitive") val sensitive: Boolean = false
)