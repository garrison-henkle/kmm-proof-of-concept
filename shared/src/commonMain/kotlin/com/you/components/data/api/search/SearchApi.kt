package com.you.components.data.api.search

import com.you.components.data.api.SearchableApi
import com.you.components.data.api.YouApi
import com.you.components.data.api.parse
import com.you.components.data.api.youApiCall
import com.you.components.data.dto.search.SearchResult
import com.you.components.data.model.Webpage
import com.you.components.utils.Response
import com.you.components.utils.client
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.serialization.Serializable

interface SearchApi : SearchableApi<WebpageList, YouApi.Parameters>

class YouSearchApi : SearchApi {
    override suspend fun search(
        parameters: YouApi.Parameters
    ): Response<WebpageList> {
        val response = client.request {
            youApiCall(
                query = parameters.query,
                site = parameters.site,
                count = parameters.count,
                page = parameters.page,
                freshness = parameters.freshness,
                path = arrayOf("api", "search")
            )
        }
        return response.parse {
            val result = response.body<SearchResult>()
            val webpages = result.searchResults.mainline.bing_search_results
                .map(Webpage.Companion::fromSearchResult)
            WebpageList(webpages)
        }
    }
}

@Serializable
data class WebpageList(val data: List<Webpage>)
