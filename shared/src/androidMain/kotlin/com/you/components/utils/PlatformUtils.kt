package com.you.components.utils

import co.touchlab.kermit.Logger
import io.ktor.client.*
import io.ktor.client.engine.android.*
import kotlin.time.Duration

internal actual val client: HttpClient = HttpClient(Android) { installPlugins() }

actual fun String.parseISO8601Duration(): String =
    Duration.parseIsoString(this).inWholeSeconds.let { seconds ->
        val hour = seconds / SECONDS_IN_HOUR
        val min = (seconds % SECONDS_IN_HOUR) / SECONDS_IN_MIN
        val sec = seconds % SECONDS_IN_MIN
        when {
            hour > 0 -> "%d:%02d:%02d".format(hour, min, sec)
            min > 0 -> "%d:%02d".format(min, sec)
            else -> "0:%02d".format(sec)
        }
    }