package com.kotlineering.ksoc.client.remote.auth

import com.kotlineering.ksoc.client.domain.auth.AuthInfo
import com.kotlineering.ksoc.client.domain.auth.AuthService
import com.kotlineering.ksoc.client.remote.ApiResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.util.toLowerCasePreservingASCIIRules

class AuthApi(private val httpClient: HttpClient) {
    @kotlinx.serialization.Serializable
    data class LoginRequest(
        val type: String,
        val code: String
    )

    @kotlinx.serialization.Serializable
    data class RefreshRequest(
        val token: String
    )

    // TODO: Common BaseURL/ContentType function..

    suspend fun login(
        type: AuthService.AuthType,
        code: String
    ) = httpClient.post("http://10.0.2.2:8080/login") {
        contentType(ContentType.Application.Json)
        setBody(
            LoginRequest(
                type.name.toLowerCasePreservingASCIIRules(), code
            )
        )
    }.let { response ->
        if (response.status.isSuccess()) {
            ApiResult.Success(response.body<AuthInfo>())
        } else {
            ApiResult.Failure(response.status.description)
        }
    }

    // client as parameter, as we might need to use one passed in instead of default
    suspend fun refresh(
        refreshToken: String,
        client: HttpClient = httpClient
    ) = client.post("http://10.0.2.2:8080/refresh") {
        contentType(ContentType.Application.Json)
        setBody(
            RefreshRequest(refreshToken)
        )
    }
}
