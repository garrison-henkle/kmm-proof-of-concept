package com.you.components.data.dto.twitter

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("photo")
data class TweetImageMedia(
    override val id: Long,
    override val sizes: TweetMediaSizes,
    @SerialName("media_url_https") override val mediaUrl: String,
    @SerialName("url") override val shortenedUrl: String,
    @SerialName("display_url") override val displayUrl: String,
    @SerialName("expanded_url") override val expandedUrl: String,
    override val indices: List<Int>,
) : TweetMedia()
