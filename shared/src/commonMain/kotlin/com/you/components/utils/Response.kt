package com.you.components.utils

sealed interface Response<T> {
    data class Success<T>(val response: T) : Response<T>
    data class Error<T>(
        val ex: Exception,
        val status: Int? = null,
        val message: String? = null
    ) : Response<T>
}