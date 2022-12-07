package com.you.components.data.model

import kotlinx.serialization.Serializable

@Serializable
enum class Freshness {
    Day,
    Week,
    Any
}
