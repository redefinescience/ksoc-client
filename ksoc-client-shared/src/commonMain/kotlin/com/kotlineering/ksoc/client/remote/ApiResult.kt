package com.kotlineering.ksoc.client.remote

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess

sealed interface ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>
    data class Failure(val error: String) : ApiResult<Nothing>
}

suspend inline fun <reified T> HttpResponse.apiResult() = if(status.isSuccess()) {
    ApiResult.Success(this.body<T>())
} else {
    ApiResult.Failure(status.description)
}
