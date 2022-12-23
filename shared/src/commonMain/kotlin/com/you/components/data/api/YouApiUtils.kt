package com.you.components.data.api

import com.you.components.BuildKonfig
import com.you.components.data.model.Freshness
import com.you.components.utils.Response
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.SerializationException

object YouApi {
    const val queryKey = "q"
    const val countKey = "count"
    const val pageKey = "page"
    const val freshnessKey = "freshness"
    const val siteKey = "site"
    const val domainKey = "domain"
    const val safeSearchKey = "safeSearch"
    const val searchDomain = "search"

    data class Parameters(
        val query: String,
        val site: String?,
        val count: Int,
        val page: Int,
        val freshness: Freshness
    )
}

enum class SafeSearch {
    Off,
    Moderate,
    Strict
}

interface SearchableApi<T : Any, P : Any> {
    suspend fun search(parameters: P): Response<T>
}

/**
 * Builds a request to the You.com API
 *
 * @param query The search query.
 * @param site The optional site to search on.
 * @param count The number of results to return.
 * @param page The page of results to return.
 * @param freshness The maximum age of results.
 * @param path The parts of the path to the endpoint, i.e. /api/search would be "api", "search"
 */
fun HttpRequestBuilder.youApiCall(
    query: String,
    site: String?,
    count: Int,
    page: Int,
    freshness: Freshness,
    vararg path: String
) {
    method = HttpMethod.Get
    url {
        protocol = URLProtocol.HTTPS
        host = BuildKonfig.apiDomain
        path(*path)
        parameter(YouApi.queryKey, query)
        parameter(YouApi.countKey, count)
        parameter(YouApi.domainKey, YouApi.searchDomain)
        parameter(YouApi.pageKey, page)
        parameter(YouApi.safeSearchKey, SafeSearch.Strict.name)
        site?.also { parameter(YouApi.siteKey, it) }
        if (freshness != Freshness.Any) {
            parameter(YouApi.freshnessKey, freshness.name.lowercase())
        }
    }
}

/**
 * Builds a [Response] object from the provided [HttpResponse].
 *
 * A successful request will use the provided [parse] lambda to create a [Response.Success] object.
 *
 * Failed requests will result in a [Response.Error] containing the error.
 *
 * @param parse The lambda to parse successful API calls
 */
suspend fun <T : Any> HttpResponse.parse(
    parse: suspend HttpResponse.() -> T
): Response<T> = when (status) {
    HttpStatusCode.OK -> {
        try {
            Response.Success(parse())
        } catch (ex: SerializationException) {
            Response.Error(ex)
        }
    }
    else -> Response.Error(
        ex = Exception(bodyAsText()),
        status = status.value
    )
}

/**
 * Indicates that a class has a thumbnail that can be scaled. The scaled thumbnail can be requested
 * with [getHigherResolutionUrl].
 */
abstract class HasScalableThumbnail {
    abstract val thumbnailUrl: String?
    abstract val thumbnailWidth: Int?
    abstract val thumbnailHeight: Int?

    /**
     * Builds a formatted url to retrieve higher resolution images from Bing.
     */
    fun getHigherResolutionUrl(desiredSize: Int): String? = thumbnailUrl?.let { url ->
        //need to force the smaller of the two to be equal to size so that the larger is greater than or equal to size
        val smallerSide = if ((thumbnailWidth ?: 0) < (thumbnailHeight ?: 0)) "w" else "h"
        //crop (c) can be blind ratio (4) or smart ratio (7). Smart tries to keep the region of interest
        "$url?$smallerSide=$desiredSize&c=7"
    }
}
