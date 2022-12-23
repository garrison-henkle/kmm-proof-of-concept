package com.you.components.data.dto.twitter

import kotlinx.serialization.Serializable

@Serializable
sealed class TweetIndexedEntity {
    abstract val indices: List<Int>
    val start get() = indices[0]
    val end get() = indices[1]
}
