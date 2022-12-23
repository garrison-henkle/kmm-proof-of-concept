package com.you.components.data.dto.twitter

import kotlinx.serialization.*

@Serializable
sealed class TweetMedia : TweetIndexedEntity() {
    abstract val id: Long
    abstract val sizes: TweetMediaSizes
    abstract val mediaUrl: String
    abstract val shortenedUrl: String
    abstract val displayUrl: String
    abstract val expandedUrl: String
}
