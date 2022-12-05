package com.you.components.utils

import co.touchlab.kermit.Logger
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

internal const val SECONDS_IN_MIN = 60
internal const val SECONDS_IN_HOUR = 60 * SECONDS_IN_MIN

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
