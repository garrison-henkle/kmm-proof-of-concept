@file:Suppress("PropertyName")

package com.you.components.utils

import kotlinx.coroutines.CoroutineDispatcher

/**
 * CDispatchers (CommonDispatchers) contains dispatcher implementations for each platform
 */
expect object CDispatchers {
    val IO: CoroutineDispatcher
    val Main: CoroutineDispatcher
}