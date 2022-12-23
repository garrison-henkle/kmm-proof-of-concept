package com.you.components.data.dto.twitter

import kotlinx.serialization.Serializable

@Serializable
data class TwitterSearchResponse(val statuses: List<TweetData>)