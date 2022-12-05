package com.you.tester

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.you.components.data.api.video.YouVideoApi
import com.you.components.data.model.Freshness
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

        }

        val api = YouVideoApi()

        lifecycleScope.launch {
            val result = api.search(
                query = "United States",
                site = null,
                resultCount = 25,
                freshness = Freshness.Day
            )
            android.util.Log.i("You.com", "result: $result")
        }
    }
}
