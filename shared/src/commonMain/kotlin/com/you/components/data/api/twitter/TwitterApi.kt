package com.you.components.data.api.twitter

import co.touchlab.kermit.Logger
import com.you.components.data.api.SearchableApi
import com.you.components.data.api.parse
import com.you.components.data.dto.twitter.*
import com.you.components.data.model.Tweet
import com.you.components.utils.*
import com.you.components.utils.TWITTER_DATE_PARSER_FORMAT
import com.you.components.utils.client
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.http.HttpHeaders.Authorization
import kotlinx.datetime.Clock
import kotlinx.serialization.SerializationException

interface TwitterApi : SearchableApi<TweetList, TwitterApiParameters>

class TwitterApiImpl(apiToken: String) : TwitterApi {
    companion object {
        private const val twitterHost = "api.twitter.com"
        private val searchPath = arrayOf("1.1", "search", "tweets.json")
        private val trendsPath = arrayOf("1.1", "trends", "place.json")
        private const val orOperator = "+OR+"
        private const val maxQueryLength = 450
        private const val ISO_3166_US = "US"
        private const val WOEID_US = 23424977
        private const val WOEID_GLOBAL = 1
        private const val resultTypeKey = "result_type"
        private const val popular = "popular"
        private const val tweetModeKey = "tweet_mode"
        private const val extended = "extended"
        private const val languageKey = "lang"
        private const val english = "en"
        private const val countKey = "count"
        private const val queryKey = "q"
    }

    private val bearer = "Bearer $apiToken"
    private val woeid = if (getLocale().country == ISO_3166_US) WOEID_US else WOEID_GLOBAL

    private suspend fun getTrends(): Response<List<TweetTrend>> {
        val response = client.request {
            method = HttpMethod.Get
            url {
                protocol = URLProtocol.HTTPS
                host = twitterHost
                path(*trendsPath)
                header(Authorization, bearer)
                parameter("id", woeid)
            }
        }
        return response.parse {
            val result = response.body<List<TwitterTrendsResponse>>()
            result.first().trends
        }
    }

    override suspend fun search(parameters: TwitterApiParameters): Response<TweetList> {
        var trendNames: List<String> = emptyList()
        val query = parameters.query ?: run {
            when (val trendsRequest = getTrends()) {
                is Response.Success -> {
                    trendsRequest.result
                        .sortedByDescending { it.tweetVolume }
                        .let {
                            trendNames = it.map { trend -> trend.name }
                            getSearchQueryFromTrends(it)
                        }
                }
                is Response.Error -> return Response.Error(
                    ex = trendsRequest.ex,
                    status = trendsRequest.status,
                    message = trendsRequest.message
                )
            }
        }

        val response = client.request {
            method = HttpMethod.Get
            url {
                protocol = URLProtocol.HTTPS
                host = twitterHost
                path(*searchPath)
                header(Authorization, bearer)
                parameter(resultTypeKey, popular)
                parameter(tweetModeKey, extended)
                parameter(languageKey, english)
                parameter(countKey, parameters.count)
                parameter(queryKey, query)
            }
        }

        Logger.i("You.com") { "Query is '$query'" }

        return response.parse {
            val result = response.body<TwitterSearchResponse>()
            val tweets =
                result.statuses.let { data -> if (parameters.safeFilter) data.filterNot { it.sensitive } else data }
                    .map { tweet ->
                        //Twitter sends the timestamp in a non-standard format
                        val instant = tweet.timestamp.parseInstant(TWITTER_DATE_PARSER_FORMAT)
                            ?: Clock.System.now()
                        val timestamp = instant.format(TWITTER_DATE_FORMATTER_FORMAT)
                            .replaceFirst("%", instant.getOrdinalSuffix())
                        val trend = tweet.text.findAnyOf(trendNames, ignoreCase = true)
                        val (text, entities, media) = tweet.decode()

                        Tweet(
                            id = tweet.id,
                            text = text,
                            entities = entities,
                            handle = "@${tweet.user.handle}",
                            name = tweet.user.name,
                            profileImageUrl = tweet.user.profileImageUrl,
                            timestamp = timestamp,
                            retweets = tweet.retweets,
                            likes = tweet.likes,
                            media = media,
                            trend = trend?.second,
                        )
                    }

            TweetList(
                if (parameters.shuffle) {
                    val shuffledTweets = mutableListOf<Tweet>()

                    val partitioned = tweets
                        .groupBy { it.trend }
                        .mapValues { (_, values) -> values.toMutableList() }
                        .toList()
                        .reversed()
                        .toMutableList()
                    var trendTweets: List<Tweet>
                    while (partitioned.isNotEmpty()) {
                        for (i in partitioned.lastIndex downTo 0) {
                            trendTweets = partitioned[i].second
                            shuffledTweets += trendTweets.removeFirst()
                            if (trendTweets.isEmpty()) partitioned.removeAt(i)
                        }
                    }

                    shuffledTweets
                } else tweets
            )
        }
    }

    private fun getSearchQueryFromTrends(trends: List<TweetTrend>): String {
        var limit = 0
        var characters = 0
        val orOperatorLength = orOperator.length
        for ((i, trend) in trends.withIndex().asSequence().drop(1)) {
            val newCharacters = trend.query.length + orOperatorLength
            if (characters + newCharacters > maxQueryLength) break
            characters += newCharacters
            limit = i
        }
        return trends
            .joinToString(separator = orOperator, limit = limit, truncated = "") { it.query }
            //joinString apparently keeps the separator if truncation occurs, so we need to chop it off
            .dropLast(orOperator.length)
    }

    /**
     * Decodes the percent encoding of the tweet and processes indices to account for UTF-16 4-byte characters
     *
     * Java uses UTF16 so some characters might take up 2 indices and break twitter's indexing. Indices are adjusted to
     * ensure entities point to the correct character ranges.
     */
    private fun TweetData.decode(): Triple<String, List<TweetEntity>, List<TweetMedia>> {
        val decodedText = try {
            text.decodeURLQueryComponent(plusIsSpace = true)
        } catch (ex: Exception) {
            text
        }

        val utf16Indices = decodedText.findMultipleCharUTF16Characters()
        val entities = entities.hashtags + entities.mentions + entities.urls
        val media = extendedEntities?.media ?: emptyList()
        return if (utf16Indices.isEmpty()) {
            Triple(decodedText, entities, media)
        } else {
            //algorithm to correct the indices:
            // 1) sort indexable entities by their starting index and create an array that will indicate
            //    where increments in indices will occur
            // 2) adjust the utf16 indices to account for the utf16 characters before them
            // 3) iterate over the known utf16 characters and find the first entity that has a starting
            //    position >= the utf16 character position and increment the increments array
            // 4) update the entities using the increment array by keeping a running sum and adding it
            //    to each set of indices

            //1
            val indexable = (entities + media).sortedBy { it.start }
            val increments = IntArray(indexable.size)

            //2
            val adjustedUtf16Indices = utf16Indices.toMutableList()
            for (i in 1 until adjustedUtf16Indices.size) {
                adjustedUtf16Indices[i] -= i
            }

            //3
            outer@ for (utf8CharIndex in adjustedUtf16Indices) {
                for ((i, item) in indexable.withIndex()) {
                    if (item.start >= utf8CharIndex) {
                        increments[i] += 1
                        continue@outer
                    }
                }
            }

            val updatedEntities = mutableListOf<TweetEntity>()
            val updatedMedia = mutableListOf<TweetMedia>()

            //4
            var currentSum = 0
            for ((i, item) in indexable.withIndex()) {
                currentSum += increments[i]

                @Suppress("REDUNDANT_ELSE_IN_WHEN") //compiler throws an error w/o else
                when (item) {
                    is TweetMention ->
                        updatedEntities += item.copy(
                            indices = listOf(item.start + currentSum, item.end + currentSum)
                        )
                    is TweetHashtag ->
                        updatedEntities += item.copy(
                            indices = listOf(item.start + currentSum, item.end + currentSum)
                        )
                    is TweetUrl ->
                        updatedEntities += item.copy(
                            indices = listOf(item.start + currentSum, item.end + currentSum)
                        )
                    is TweetVideoMedia ->
                        updatedMedia += item.copy(
                            indices = listOf(item.start + currentSum, item.end + currentSum)
                        )
                    is TweetImageMedia ->
                        updatedMedia += item.copy(
                            indices = listOf(item.start + currentSum, item.end + currentSum)
                        )
                    else -> throw SerializationException("invalid TweetIndexedEntity type")
                }
            }

            return Triple(decodedText, updatedEntities, updatedMedia)
        }
    }

    /**
     * Finds the indices for the first Kotlin char in every UTF16 multiple-Kotlin-char character.
     */
    private fun String.findMultipleCharUTF16Characters(): List<Int> {
        val indices = mutableListOf<Int>()
        forEachIndexed { i, c ->
            if (c.isHighSurrogate()) {
                indices += i
            }
        }
        return indices
    }
}