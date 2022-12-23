package com.you.components.utils

import com.you.components.data.model.Locale
import io.ktor.client.*
import kotlinx.datetime.Instant

/**
 * Client for making HTTP requests
 */
internal expect val client: HttpClient

/**
 * Parses an ISO8601 duration string and formats it for display to the user.
 *
 * Example outputs: 0:01, 0:23, 4:56, 1:23:45 etc.
 *
 * This is implemented on a per-platform basis because passing data via a variadic Any? parameter
 * loses the typing data needed by the platform specific formatters.
 */
expect fun String.parseISO8601Duration(): String

/**
 * Retrieves the user's current locale.
 */
expect fun getLocale(): Locale

/**
 * Parses this string as an ISO-8601 date string using the provided Unicode #35 [pattern].
 */
expect fun String.parseInstant(pattern: String): Instant?

/**
 * Formats this Instant into a string using the provided Unicode #35 [pattern].
 */
expect fun Instant.format(pattern: String): String
