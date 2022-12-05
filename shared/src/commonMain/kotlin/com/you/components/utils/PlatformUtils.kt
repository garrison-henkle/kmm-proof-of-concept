package com.you.components.utils

import io.ktor.client.*

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