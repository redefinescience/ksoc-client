package com.kotlineering.ksoc.client.remote

sealed interface ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>
    data class Failure(val error: String) : ApiResult<Nothing>
}
