package com.you.components.data.repository.video

import com.you.components.data.api.video.VideoApi
import com.you.components.data.model.Freshness
import com.you.components.data.model.Video
import com.you.components.data.model.enums.TrendingVideoCategory
import com.you.components.utils.CachedEndpoint
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString

class TrendingVideoRepository(private val api: VideoApi, filesDir: String) {
    private val endpoint = CachedEndpoint(
        name = "video",
        fetch = { parameters ->
            val query: String = parameters.getOrElse("q"){ "" } as String
            val site: String? = parameters.getOrElse("site"){ null } as String?
            val count: Int = parameters.getOrElse("count"){ 25 } as Int
            val freshness: Freshness = parameters.getOrElse("freshness"){ Freshness.Day } as Freshness
            api.search(
                query = query,
                site = site,
                resultCount = count,
                freshness = freshness
            )
        },
        serialize = { video -> encodeToString(video) },
        deserialize = { json -> decodeFromString(json) },
        isEmpty = { data.isEmpty() },
        androidFilesDir = filesDir,
    )

    fun getVideos(category: TrendingVideoCategory): List<Video>?{
        
    }
}