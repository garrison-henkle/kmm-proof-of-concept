package com.you.components.data.model

import com.you.components.data.dto.twitter.TweetEntity
import com.you.components.data.dto.twitter.TweetMedia

import kotlinx.serialization.Serializable

@Serializable
data class Tweet(
    val id: String,
    val text: String,
    val entities: List<TweetEntity>,
    val handle: String,
    val name: String,
    val profileImageUrl: String,
    val timestamp: String,
    val retweets: Int,
    val likes: Int,
    val media: List<TweetMedia>,
    val trend: String?
)