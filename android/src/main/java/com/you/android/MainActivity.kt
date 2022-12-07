package com.you.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.you.components.data.api.video.YouVideoApi
import com.you.components.data.repository.VideoRepository
import com.you.components.utils.CachedEndpoint
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

        }

        val repo = VideoRepository(YouVideoApi(), filesDir.absolutePath)
        lifecycleScope.launch {
            CachedEndpoint.initializeCacheDirectory(androidFilesDir = filesDir.absolutePath)
            val results =
                repo.getVideos(query = "hamsters drive cars across the united states during their hunting season to try to find humans that look like easy pickings. This is a speedrun video that does not actually demonstrate any speedrunning or speedrunning skills. If I make this any longer, I believe it will return absolutely no results back from the API. www.www.ww.ww.ww.ww.")
            android.util.Log.i("You.com", "results: $results")
        }
    }
}
