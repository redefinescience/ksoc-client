package com.kotlineering.ksoc.client.http

import com.kotlineering.ksoc.client.auth.AuthInfo
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.plugin
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

class HttpClientManager(
    engine: HttpClientEngine
) {
    val client: HttpClient = createHttpClient(engine)
    val isAuthSet: Boolean get() = client.plugin(Auth).providers.size == 0

    fun setAuth(
        auth: AuthInfo?,
        refresh: (() -> BearerTokens?)?
    ) = client.apply {
        plugin(Auth).apply {
            providers.clear()
            auth?.let {
                bearer {
                    loadTokens { BearerTokens(auth.bearer, auth.refresh) }
                    refreshTokens { refresh?.invoke() }
                }
            }
        }
    }
}

@OptIn(ExperimentalSerializationApi::class)
private fun createHttpClient(
    engine: HttpClientEngine
) = HttpClient(engine) {
    install(ContentNegotiation) {
        json(Json {
            isLenient = true
            ignoreUnknownKeys = true
            explicitNulls = false
        })
    }
    install(Auth)
}
