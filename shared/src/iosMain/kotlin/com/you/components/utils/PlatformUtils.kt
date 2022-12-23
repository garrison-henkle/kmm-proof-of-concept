package com.you.components.utils

import com.you.components.data.model.Locale
import io.ktor.client.*
import io.ktor.client.engine.darwin.*
import kotlinx.datetime.Instant
import kotlinx.datetime.toKotlinInstant
import kotlinx.datetime.toNSDate
import platform.Foundation.*
import kotlin.time.Duration

internal actual val client = HttpClient(Darwin) { installPlugins() }

actual fun String.parseISO8601Duration(): String =
    Duration.parseIsoString(this).inWholeSeconds.let { seconds ->
        val hour = (seconds / SECONDS_IN_HOUR).toInt()
        val min = ((seconds % SECONDS_IN_HOUR) / SECONDS_IN_MIN).toInt()
        val sec = (seconds % SECONDS_IN_MIN).toInt()
        when {
            hour > 0 -> NSString.stringWithFormat(VIDEO_DURATION_FORMAT_HOURS, hour, min, sec)
            min > 0 -> NSString.stringWithFormat(VIDEO_DURATION_FORMAT_MINUTES, min, sec)
            else -> NSString.stringWithFormat(VIDEO_DURATION_FORMAT_SECONDS, sec)
        }
    }

private const val defaultCountry = "US"

actual fun getLocale(): Locale = NSLocale.currentLocale.run {
    Locale(
        country = countryCode ?: defaultCountry,
        language = languageCode
    )
}

actual fun String.parseInstant(pattern: String): Instant? =
    formatterCache.getOrPut(pattern) {
        NSDateFormatter().apply { dateFormat = pattern }
    }.dateFromString(this)?.toKotlinInstant()

actual fun Instant.format(pattern: String): String =
    formatterCache.getOrPut(pattern) {
        NSDateFormatter().apply { dateFormat = pattern }
    }.stringFromDate(toNSDate())
