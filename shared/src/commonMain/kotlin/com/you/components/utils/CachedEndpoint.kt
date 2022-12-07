package com.you.components.utils

import com.you.components.data.api.SearchableApi
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.json.Json

/**
 * Endpoint wrapper that caches successful responses as JSON.
 *
 * @param name The name of the endpoint (used in the cache filename).
 * @param api API to fetch data from.
 * @param serialize Lambda to serialize the response data as a string.
 * @param deserialize Lambda to deserialize the response data from a string.
 * @param isEmpty Lambda to check if the given response data is in an empty state.
 * @param androidFilesDir The absolute path to the android files directory. This is necessary
 * because of the lack of android Context, so this can be safely left as null on iOS.
 * @param cacheExpirationMs The time in milliseconds before the cache is invalidated.
 */
class CachedEndpoint<T : Any, P : Any>(
    private val name: String,
    private val api: SearchableApi<T, P>,
    private val serialize: Json.(T) -> String,
    private val deserialize: Json.(String) -> T,
    private val isEmpty: T.() -> Boolean,
    private val androidFilesDir: String? = null,
    private val cacheExpirationMs: Long = 43_200_000L, //12 hours by default
) {
    companion object {
        private val serializer = Json {
            isLenient = true
            ignoreUnknownKeys = true
        }
        private const val directory = "apiCache"
        suspend fun initializeCacheDirectory(androidFilesDir: String? = null) =
            withContext(CDispatchers.IO) {
                CFile(
                    filename = directory,
                    androidFilesDirPath = androidFilesDir
                ).createDirectories()
            }
    }

    suspend fun get(
        parameters: P,
        id: Int = 0,
    ): Response<T> = withContext(CDispatchers.IO) {
        val filename = "$directory/$name-$id.cache"
        val cache = if (CFile.exists(filename, androidFilesDirPath = androidFilesDir)) {
            CFile(filename, androidFilesDirPath = androidFilesDir).readAll()
        } else null
        cache?.let { parseCacheString(it) }
            ?: api.search(parameters).also { response ->
                if (response is Response.Success) {
                    val data = response.result
                    val isNotEmpty = !data.isEmpty()
                    if (isNotEmpty) {
                        val cacheString = generateCacheString(data)
                        CFile(filename, androidFilesDirPath = androidFilesDir).write(cacheString)
                    }
                }
            }
    }

    private fun generateCacheString(data: T): String {
        val timestamp = Clock.System.now().toString()
        val serializedData = serializer.serialize(data)
        return "$timestamp|$serializedData"
    }

    private fun parseCacheString(cacheString: String): Response<T>? {
        val dividerIndex = cacheString.indexOfFirst { it == '|' }
        val timestampStr = cacheString.substring(0 until dividerIndex)
        val serializedData = cacheString.substring(dividerIndex + 1)
        val timestamp = Instant.parse(timestampStr)
        val elapsedTime = Clock.System.now() - timestamp
        return if (elapsedTime.inWholeMilliseconds <= cacheExpirationMs) {
            try {
                Response.Success(serializer.deserialize(serializedData))
            } catch (ex: Exception) {
                Response.Error(ex)
            }
        } else null
    }
}
