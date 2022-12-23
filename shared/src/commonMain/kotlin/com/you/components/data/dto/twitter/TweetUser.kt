package com.you.components.data.dto.twitter

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TweetUser(
    val id: String,
    val name: String,
    @SerialName("screen_name") val handle: String,
    val verified: Boolean,
    @SerialName("profile_image_url_https") val profileImageUrl: String,
)