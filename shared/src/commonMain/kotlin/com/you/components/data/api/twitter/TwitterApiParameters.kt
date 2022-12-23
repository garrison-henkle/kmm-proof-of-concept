package com.you.components.data.api.twitter

data class TwitterApiParameters(
    val query: String? = null,
    val count: Int = 40,
    val safeFilter: Boolean = true,
    val shuffle: Boolean = true
)