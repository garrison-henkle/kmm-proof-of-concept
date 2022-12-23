package com.you.components.data.api.video

import com.you.components.data.api.SearchableApi
import com.you.components.data.api.YouApi
import com.you.components.data.api.parse
import com.you.components.data.api.youApiCall
import com.you.components.data.dto.video.VideoResult
import com.you.components.data.model.Video
import com.you.components.utils.Response
import com.you.components.utils.client
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.serialization.Serializable

interface VideoApi : SearchableApi<VideoList, YouApi.Parameters>

class YouVideoApi : VideoApi {
    override suspend fun search(
        parameters: YouApi.Parameters
    ): Response<VideoList> {
        val response = client.request {
            youApiCall(
                query = parameters.query,
                site = parameters.site,
                count = parameters.count,
                page = parameters.page,
                freshness = parameters.freshness,
                path = arrayOf("api", "video")
            )
        }
        return response.parse {
            val result = response.body<VideoResult>()
            val videos = result.searchResults.results
                .map(Video.Companion::fromVideoResult)
            VideoList(videos)
        }
    }
}

@Serializable
data class VideoList(val data: List<Video>)
