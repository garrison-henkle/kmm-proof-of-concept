package com.you.components.utils

import io.ktor.client.*
import io.ktor.client.engine.darwin.*
import platform.Foundation.NSString
import platform.Foundation.stringWithFormat
import kotlin.time.Duration

internal actual val client = HttpClient(Darwin) { installPlugins() }

actual fun String.parseISO8601Duration(): String =
    Duration.parseIsoString(this).inWholeSeconds.let { seconds ->
        val hour = (seconds / SECONDS_IN_HOUR).toInt()
        val min = ((seconds % SECONDS_IN_HOUR) / SECONDS_IN_MIN).toInt()
        val sec = (seconds % SECONDS_IN_MIN).toInt()
        when {
            hour > 0 -> NSString.stringWithFormat("%d:%02d:%02d", hour, min, sec)
            min > 0 -> NSString.stringWithFormat("%d:%02d", min, sec)
            else -> NSString.stringWithFormat("0:%02d", sec)
        }
    }
