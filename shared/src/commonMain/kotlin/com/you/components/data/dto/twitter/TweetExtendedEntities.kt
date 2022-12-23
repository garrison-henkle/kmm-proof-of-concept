package com.you.components.data.dto.twitter

import kotlinx.serialization.Serializable

@Serializable
data class TweetExtendedEntities(val media: List<TweetMedia>)