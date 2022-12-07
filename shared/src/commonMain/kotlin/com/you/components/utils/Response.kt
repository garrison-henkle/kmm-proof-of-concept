package com.you.components.utils

sealed interface Response<T : Any> {
    data class Success<T : Any>(val result: T) : Response<T>
    data class Error<T : Any>(
        val ex: Exception,
        val status: Int? = null,
        val message: String? = null
    ) : Response<T>
}