package com.you.components.data.repository

import com.you.components.data.api.YouApi
import com.you.components.data.api.video.VideoApi
import com.you.components.data.api.video.VideoList
import com.you.components.data.model.Freshness
import com.you.components.utils.CachedEndpoint
import com.you.components.utils.Response
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString

class VideoRepository(api: VideoApi, filesDir: String?) {
    private val endpoint = CachedEndpoint(
        name = "video",
        api = api,
        serialize = { video -> encodeToString(video) },
        deserialize = { json -> decodeFromString(json) },
        isEmpty = { data.isEmpty() },
        androidFilesDir = filesDir,
    )

    suspend fun getVideos(
        query: String,
        site: String? = null,
        count: Int = 25,
        page: Int = 1,
        freshness: Freshness = Freshness.Day,
        id: Int = 0
    ): Response<VideoList> = endpoint.get(
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