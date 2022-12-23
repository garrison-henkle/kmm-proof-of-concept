package com.you.components.data.dto.twitter

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("mention")
data class TweetMention(
    @SerialName("id") val userId: String,
    @SerialName("screen_name") val handle: String,
    val name: String,
    override val indices: List<Int>,
) : TweetEntity()