package com.you.components.data.dto.twitter

import kotlinx.serialization.Serializable

@Serializable
enum class TweetEntityType {
    Hashtag,
    Mention,
    Url
}