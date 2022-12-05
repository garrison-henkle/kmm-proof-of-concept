package com.you.components.utils

import io.ktor.utils.io.core.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*

/**
 * CFlow is a wrapper around flow that allows for iOS to safely close its observer
 */
class CFlow<T : Any>(private val source: Flow<T>) : Flow<T> by source {
    fun observe(block: (T) -> Unit): Closeable {
        val job = Job()

        onEach { block(it) }.launchIn(CoroutineScope(job + CDispatchers.Main))

        return object : Closeable {
            override fun close() {
                job.cancel()
            }
        }
    }

    fun asStateFlow(
        scope: CoroutineScope,
        initialValue: T,
        started: SharingStarted = SharingStarted.Eagerly,
    ): CStateFlow<T> = source.stateIn(
        scope = scope,
        started = started,
        initialValue = initialValue
    ).wrap()
}

fun <T : Any> Flow<T>.wrap(): CFlow<T> = CFlow(this)