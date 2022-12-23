package com.you.components.utils

import co.touchlab.kermit.Logger
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.offsetIn
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.json.Json

internal const val SECONDS_IN_MIN = 60
internal const val SECONDS_IN_HOUR = 60 * SECONDS_IN_MIN
internal const val VIDEO_DURATION_FORMAT_HOURS = "%d:%02d:%02d"
internal const val VIDEO_DURATION_FORMAT_MINUTES = "%d:%02d"
internal const val VIDEO_DURATION_FORMAT_SECONDS = "0:%02d"
internal const val TWITTER_DATE_FORMATTER_FORMAT = "hh:mm a · MMMM dd%, yyyy"
internal const val TWITTER_DATE_PARSER_FORMAT = "EEE MMM d k:m:s Z y"

internal fun HttpClientConfig<*>.installPlugins() {
    install(ContentNegotiation) {
        json(Json {
            isLenient = true
            ignoreUnknownKeys = true
        })
    }
    install(Logging) {
        logger = object : io.ktor.client.plugins.logging.Logger {
            override fun log(message: String) {
                Logger.d("LightN") { message }
            }
        }
        level = LogLevel.ALL
    }
}

/**
 * Removes hit markers from a string returned by the Bing API.
 *
 * The hit markers are represented by Unicode E000 and E001
 */
fun String.removeHitMarkers(): String {
    val builder = StringBuilder()
    for (char in this) {
        if (char != '\ue000' && char != '\ue001') {
            builder.append(char)
        }
    }
    return builder.toString()
}

/**
 * Get ordinal suffix for the given number ie "1" to "1st", "2" to "2nd", etc.
 */
fun Instant.getOrdinalSuffix(): String =
    when (toLocalDateTime(TimeZone.currentSystemDefault()).dayOfMonth % 10) {
        1 -> "st"
        2 -> "nd"
        3 -> "rd"
        else -> "th"
    }
