package com.you.components.utils

import com.you.components.data.model.Locale
import io.ktor.client.*
import io.ktor.client.engine.android.*
import kotlinx.datetime.Instant
import kotlinx.datetime.toJavaInstant
import kotlinx.datetime.toKotlinInstant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import kotlin.time.Duration

internal actual val client: HttpClient = HttpClient(Android) { installPlugins() }

actual fun String.parseISO8601Duration(): String =
    Duration.parseIsoString(this).inWholeSeconds.let { seconds ->
        val hour = seconds / SECONDS_IN_HOUR
        val min = (seconds % SECONDS_IN_HOUR) / SECONDS_IN_MIN
        val sec = seconds % SECONDS_IN_MIN
        when {
            hour > 0 -> VIDEO_DURATION_FORMAT_HOURS.format(hour, min, sec)
            min > 0 -> VIDEO_DURATION_FORMAT_MINUTES.format(min, sec)
            else -> VIDEO_DURATION_FORMAT_SECONDS.format(sec)
        }
    }

actual fun getLocale(): Locale {
    return java.util.Locale.getDefault().run {
        Locale(
            country = country,
            language = language
        )
    }
}

actual fun String.parseInstant(pattern: String): Instant? = try {
    formatterCache.getOrPut(pattern) {
        DateTimeFormatter.ofPattern(pattern, java.util.Locale.getDefault())
    }.parse(this, java.time.Instant::from).toKotlinInstant()
} catch (ex: DateTimeParseException) {
    null
}

actual fun Instant.format(pattern: String): String =
    formatterCache.getOrPut(pattern) {
        DateTimeFormatter.ofPattern(pattern, java.util.Locale.getDefault())
    }.format(toJavaInstant().atZone(ZoneId.systemDefault()))
