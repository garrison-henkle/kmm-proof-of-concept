package com.you.components.data.dto.twitter

import kotlinx.serialization.Serializable

@Serializable
data class TwitterTrendsResponse(val trends: List<TweetTrend>)