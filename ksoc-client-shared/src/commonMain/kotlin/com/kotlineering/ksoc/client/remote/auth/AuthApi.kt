package com.kotlineering.ksoc.client.remote.auth

import com.kotlineering.ksoc.client.domain.auth.AuthInfo
import com.kotlineering.ksoc.client.domain.auth.AuthService
import com.kotlineering.ksoc.client.domain.user.UserInfo
import com.kotlineering.ksoc.client.remote.ApiResult
import com.kotlineering.ksoc.client.remote.apiResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.encodedPath
import io.ktor.http.isSuccess
import io.ktor.http.takeFrom
import io.ktor.util.toLowerCasePreservingASCIIRules

class AuthApi(private val httpClient: HttpClient) {
    @kotlinx.serialization.Serializable
    data class LoginRequest(
        val type: String,
        val code: String
    )

    @kotlinx.serialization.Serializable
    data class TokenRequest(
        val bearer: String,
        val refresh: String
    )

    private fun HttpRequestBuilder.auth(path: String) {
        url {
            // TODO: Configurable, injected
            takeFrom("http://10.0.2.2:8080/")
            encodedPath = path
        }
    }

    private inline fun <reified T : Any> HttpRequestBuilder.auth(path: String, body: T) {
        auth(path)
        contentType(ContentType.Application.Json)
        setBody(body)
    }

    suspend fun updateUserProfile(
        userInfo: UserInfo
    ) = httpClient.put {
        auth("profile/${userInfo.id}", userInfo)
    }.let { response ->
        if (response.status.isSuccess()) {
            ApiResult.Success(response.body<UserInfo>())
        } else {
            ApiResult.Failure(response.status.description)
        }
    }

    suspend fun login(
        type: AuthService.AuthType,
        code: String
    ) = httpClient.post {
        auth("authorize", LoginRequest(
            type.name.toLowerCasePreservingASCIIRules(), code
        ))
    }.apiResult<AuthInfo>()

    // client as parameter, as we might need to use one passed in instead of default
    suspend fun refresh(
        bearer: String,
        refresh: String,
        client: HttpClient = httpClient
    ) = client.post {
        auth("refresh", TokenRequest(bearer, refresh))
    }.apiResult<AuthInfo>()

    suspend fun logout(
        bearer: String,
        refresh: String
    ) = httpClient.post {
        auth("revoke", TokenRequest(bearer, refresh))
    }
}
