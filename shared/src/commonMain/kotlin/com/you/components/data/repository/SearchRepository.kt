package com.you.components.data.repository

import com.you.components.data.api.YouApi
import com.you.components.data.api.search.SearchApi
import com.you.components.data.api.search.WebpageList
import com.you.components.data.model.Freshness
import com.you.components.utils.CachedEndpoint
import com.you.components.utils.Response
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString

class SearchRepository(api: SearchApi, filesDir: String?) {
    private val endpoint = CachedEndpoint(
        name = "search",
        api = api,
        serialize = { video -> encodeToString(video) },
        deserialize = { json -> decodeFromString(json) },
        isEmpty = { data.isEmpty() },
        androidFilesDir = filesDir,
    )

    suspend fun getWebpages(
        query: String,
        site: String? = null,
        count: Int = 25,
        page: Int = 1,
        freshness: Freshness = Freshness.Day,
        id: Int = 0
    ): Response<WebpageList> = endpoint.get(
        parameters = YouApi.Parameters(
            query = query,
            site = site,
            count = count,
            page = page,
            freshness = freshness
        ),
        id = id
    )
}