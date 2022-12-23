package com.you.components.data.repository

import com.you.components.data.api.twitter.TwitterApi
import com.you.components.data.api.twitter.TwitterApiParameters
import com.you.components.data.dto.twitter.TweetList
import com.you.components.utils.CachedEndpoint
import com.you.components.utils.Response
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString

class TwitterRepository(api: TwitterApi, filesDir: String?) {
    private val endpoint = CachedEndpoint(
        name = "twitter",
        api = api,
        serialize = { tweets -> encodeToString(tweets) },
        deserialize = { json -> decodeFromString(json) },
        isEmpty = { data.isEmpty() },
        androidFilesDir = filesDir
    )

    suspend fun getTweets(
        query: String? = null,
        count: Int = 40,
        safeFilter: Boolean = true,
        shuffle: Boolean = true,
        id: Int = 0,
    ): Response<TweetList> = endpoint.get(
        parameters = TwitterApiParameters(
            query = query,
            count = count,
            safeFilter = safeFilter,
            shuffle = shuffle
        ),
        id = id
    )
}