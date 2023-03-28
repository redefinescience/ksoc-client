package com.kotlineering.ksoc.client.auth

import io.ktor.client.HttpClient
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.plugin
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType

import io.ktor.http.contentType
import io.ktor.http.takeFrom
import io.ktor.util.toLowerCasePreservingASCIIRules
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.datetime.Clock

class AuthRepository(
    private val authStore: AuthStore,
    private val httpClient: HttpClient,
    private val dispatcher: CoroutineDispatcher
) {
    enum class AuthType {
        Microsoft,
        Google,
        Apple,
        Facebook,
    }

    private val mutableAuthInfoFlow = MutableStateFlow(authStore.fetchAuthInfo())

    val authInfoFlow: Flow<AuthInfo?>
        get() = mutableAuthInfoFlow

    var authInfo: AuthInfo?
        get() = mutableAuthInfoFlow.value
        set(t) = authStore.storeAuthInfo(t).also {
            httpClient.plugin(Auth).apply {
                providers.clear()
                t?.let {
                    bearer {
                        loadTokens { BearerTokens(t.bearer, t.refresh) }
                        refreshTokens { refresh() }
                    }
                }
            }
            mutableAuthInfoFlow.value = t
        }

    private fun refresh() = authInfo?.let {
        BearerTokens("", "")
    }

    fun logout() {
        authInfo = null
    }

    // TODO: Move this to API & replace login function with login2
    @kotlinx.serialization.Serializable
    data class LoginFrom(
        val type: String,
        val code: String
    )

    fun HttpRequestBuilder.apiRoot(path: String) {
        url {
            takeFrom("http://10.0.2.2:8080/")
        }
    }
    suspend fun login2(type: AuthType, code: String) {
        httpClient.post("http://10.0.2.2:8080/login") {
          //  apiRoot("login")
            contentType(ContentType.Application.Json)
            setBody(
                LoginFrom(
                    type.name.toLowerCasePreservingASCIIRules(),
                    code
                )
            )
        }
    }

    fun login(type: AuthType, idToken: String) {
        // TODO: Stub
        // We will actually get this back from backend server after validating id token.
        // Server will issue bearer & refresh token different from the id, of course.
        authInfo = AuthInfo(
            idToken,
            Clock.System.now(),
            "testRefresh",
            Clock.System.now(),
            "testUer($type)"
        )
    }
}
