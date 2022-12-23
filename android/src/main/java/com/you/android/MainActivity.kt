package com.you.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.you.components.data.api.twitter.TwitterApiImpl
import com.you.components.data.api.video.YouVideoApi
import com.you.components.data.dto.twitter.TweetImageMedia
import com.you.components.data.repository.TwitterRepository
import com.you.components.data.repository.VideoRepository
import com.you.components.utils.CachedEndpoint
import com.you.components.utils.Response
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

        }

        val twitterApiKey = getString(R.string.twitter_api_key)
        val twitterApi = TwitterApiImpl(apiToken = twitterApiKey)
        val repo = TwitterRepository(twitterApi, filesDir.absolutePath)
        lifecycleScope.launch {
            CachedEndpoint.initializeCacheDirectory(androidFilesDir = filesDir.absolutePath)
            val results = repo.getTweets()
            when (results) {
                is Response.Success -> {
                    android.util.Log.i("You.com", "results: ${results.result}")
                    results.result.data.forEach {
                        if (it.media.isNotEmpty()) {
                            android.util.Log.e(
                                "You.com",
                                "found media: ${it.media.first() is TweetImageMedia}"
                            )
                        }
                    }
                }
                is Response.Error -> android.util.Log.i(
                    "You.com",
                    "error: ${results.message}\n${results.ex.stackTraceToString()}"
                )
            }
            android.util.Log.i("You.com", "results: $results")
        }
    }
}
