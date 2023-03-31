package com.kotlineering.ksoc.client.domain

sealed interface ServiceResult<out T> {
    data class Success<T>(val data: T) : ServiceResult<T>
    data class Failure(val error: String) : ServiceResult<Nothing>
}
