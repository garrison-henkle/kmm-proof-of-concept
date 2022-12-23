package com.you.components.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * CStateFlow is a wrapper around state flow that allows for iOS to safely close its observer
 */
class CStateFlow<T : Any>(private val source: StateFlow<T>) : StateFlow<T> by source {
    fun observe(block: (T) -> Unit): Closable {
        val job = Job()

        onEach { block(it) }.launchIn(CoroutineScope(job + CDispatchers.Main))

        return object : Closable {
            override fun close() {
                job.cancel()
            }
        }
    }
}

fun <T : Any> StateFlow<T>.wrap(): CStateFlow<T> = CStateFlow(this)