package com.you.components.data.dto.twitter

import com.you.components.data.model.Tweet
import kotlinx.serialization.Serializable

@Serializable
data class TweetList(val data: List<Tweet>)