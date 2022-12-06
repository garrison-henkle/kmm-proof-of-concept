package com.you.components.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.json.Json

class CachedEndpoint<T: Any>(
    private val name: String,
    private val fetch: (parameters: Map<String, Any>) -> T?,
    private val serialize: Json.(T) -> String,
    private val deserialize: Json.(String) -> T?,
    private val androidFilesDir: String? = null,
    private val cacheExpirationMs: Long = 43_200_000L, //12 hours by default
){
    companion object{
        private val serializer = Json{
            isLenient = true
            ignoreUnknownKeys = true
        }
        private const val directory = "apiCache"
    }

    fun get(
        parameters: Map<String, Any> = emptyMap(),
        id: Int = 0,
    ): T?{
        val filename = "$directory/$name-$id.cache"
        val cache = if(CFile.exists(filename, androidFilesDirPath = androidFilesDir)){
            CFile(filename, androidFilesDirPath = androidFilesDir).readAll()
        } else null
        return cache?.let{ parseCacheString(it) }
            ?: fetch(parameters)?.also{ data ->
                val notEmptyList = (data as? List<*>)?.isEmpty() != true
                if(notEmptyList){
                    val cacheString = generateCacheString(data)
                    CFile(filename, androidFilesDirPath = androidFilesDir).write(cacheString)
                }
            }
    }

    private fun generateCacheString(data: T): String{
        val timestamp = Clock.System.now().toString()
        val serializedData = serializer.serialize(data)
        return "$timestamp|$serializedData"
    }

    private fun parseCacheString(cacheString: String): T?{
        val parts = cacheString.split('|')
        val (timestampStr, serializedData) = parts
        val timestamp = Instant.parse(timestampStr)
        val elapsedTime = Clock.System.now() - timestamp
        return if(elapsedTime.inWholeMilliseconds <= cacheExpirationMs){
            serializer.deserialize(serializedData)
        } else null
    }
}