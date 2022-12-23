package com.you.components.data.dto.twitter

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("hashtag")
data class TweetHashtag(
    @SerialName("text") val tag: String,
    override val indices: List<Int>,
) : TweetEntity()