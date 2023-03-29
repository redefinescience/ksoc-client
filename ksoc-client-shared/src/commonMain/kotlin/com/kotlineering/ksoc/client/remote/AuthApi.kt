package com.kotlineering.ksoc.client.remote

import com.kotlineering.ksoc.client.auth.AuthInfo
import com.kotlineering.ksoc.client.auth.AuthRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.util.toLowerCasePreservingASCIIRules
import kotlinx.datetime.Clock

class AuthApi(private val httpClient: HttpClient) {
    @kotlinx.serialization.Serializable
    data class LoginFrom(
        val type: String, val code: String
    )

    // TODO: Common BaseURL/ContentType function..

    suspend fun login(
        type: AuthRepository.AuthType,
        code: String
    ): ApiResult<AuthInfo> = httpClient.post("http://10.0.2.2:8080/login") {
        contentType(ContentType.Application.Json)
        setBody(
            LoginFrom(
                type.name.toLowerCasePreservingASCIIRules(), code
            )
        )
    }.let { response ->
        if (response.status.isSuccess()) {
            // TODO get response (once server implements!)
            ApiResult.Success(
                AuthInfo(
                    code,
                    Clock.System.now(),
                    "testRefresh",
                    Clock.System.now(),
                    "testUer($type)"
                )
            )
        } else {
            ApiResult.Failure(response.status.description)
        }
    }
}