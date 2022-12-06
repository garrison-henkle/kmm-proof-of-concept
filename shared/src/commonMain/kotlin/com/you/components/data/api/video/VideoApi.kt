package com.you.components.data.api.video

import com.you.components.BuildKonfig
import com.you.components.data.dto.video.VideoResult
import com.you.components.data.model.Freshness
import com.you.components.data.model.Video
import com.you.components.utils.Response
import com.you.components.utils.client
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

interface VideoApi {
    suspend fun search(
        query: String,
        site: String?,
        resultCount: Int,
        freshness: Freshness
    ): Response<VideoList>
}

class YouVideoApi : VideoApi {
    override suspend fun search(
        query: String,
        site: String?,
        resultCount: Int,
        freshness: Freshness
    ): Response<VideoList> {
        val response = client.request {
            method = HttpMethod.Get
            url {
                protocol = URLProtocol.HTTPS
                host = BuildKonfig.apiDomain
                path("api", "video")
                parameter("q", query)
                parameter("count", resultCount)
                parameter("domain", "search")
                parameter("page", 1)
                parameter("safeSearch", "Strict")
                if (freshness != Freshness.Any) {
                    parameter("freshness", freshness.name.lowercase())
                }
            }
        }
        return when (response.status) {
            HttpStatusCode.OK -> {
                val result = response.body<VideoResult>()
                val videos = result.searchResults.results.map(Video.Companion::fromVideoResult)
                Response.Success(VideoList(videos))
            }
            else -> Response.Error(
                ex = Exception(response.bodyAsText()),
                status = response.status.value
            )
        }
    }
}

data class VideoList(val data: List<Video>)
