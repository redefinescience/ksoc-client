package com.kotlineering.ksoc.client.auth

import com.kotlineering.ksoc.client.http.HttpClientManager
import com.kotlineering.ksoc.client.remote.ApiResult
import com.kotlineering.ksoc.client.remote.AuthApi
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.RefreshTokensParams
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.datetime.Clock

sealed interface LoginAttempt {
    data class Success(val userId: String) : LoginAttempt
    data class Failure(val error: String) : LoginAttempt
}

class AuthRepository(
    private val authStore: AuthStore,
    private val httpClientManager: HttpClientManager,
    private val authApi: AuthApi,
    private val dispatcher: CoroutineDispatcher
) {
    enum class AuthType {
        Microsoft, Google, Apple, Facebook,
    }

    private val mutableAuthInfo = MutableStateFlow(authStore.fetchAuthInfo())

    val authInfo = mutableAuthInfo.asStateFlow()

    private fun setAuthInfo(
        authInfo: AuthInfo?
    ) = authStore.storeAuthInfo(authInfo).also {
        httpClientManager.takeIf { it.isAuthSet }?.setAuth(authInfo) { performAutoRefresh(it) }
        mutableAuthInfo.value = authInfo
    }

    private suspend fun performAutoRefresh(
        params: RefreshTokensParams
    ): BearerTokens? = params.oldTokens?.refreshToken?.let { refreshToken ->
        authApi.refresh(refreshToken, params.client).let { result ->
            if (result.status.isSuccess()) {
                val tempToken = "asdf"
                val tempExpiry = Clock.System.now()
                val tempRefresh = "123"
                val tempRefreshExpiry = Clock.System.now()
                requireNotNull(mutableAuthInfo.value) {
                    "AuthInfo should not be null when performing refresh."
                }.let { old ->
                    mutableAuthInfo.value = AuthInfo(
                        old.userId,
                        tempToken,
                        tempExpiry,
                        tempRefresh,
                        tempRefreshExpiry
                    ).also { authStore.storeAuthInfo(it) }
                }
                BearerTokens(tempToken, tempRefresh)
            } else {
                setAuthInfo(null) // Try this, if not do below
                // mutableAuthInfo.value = null
                // authStore.storeAuthInfo(null)
                null
            }
        }
    }

    private suspend fun performRefresh(
        old: AuthInfo
    ) = authApi.refresh(old.refresh).let { result ->
        if (result.status.isSuccess()) {
            val temporary = result.bodyAsText()
            AuthInfo(
                old.userId,
                "fdsa",
                Clock.System.now(),
                "321",
                Clock.System.now()
            )
        } else {
            null
        }
    }.also {
        setAuthInfo(it)
    }

    // To be used during activity onCreate()
    // Conditionally run this (based on expiry etc..),
    // Do not set content until this is complete
    suspend fun refresh() = flow {
        emit(mutableAuthInfo.value?.let { old ->
            performRefresh(old)?.let {
                LoginAttempt.Success(old.userId)
            } ?: LoginAttempt.Failure("Failed to refresh token")
        } ?: LoginAttempt.Failure("No refresh token"))
    }

    fun logout() {
        setAuthInfo(null)
    }

    private suspend fun performLogin(
        type: AuthType, code: String
    ) = authApi.login(
        type, code
    ).let { result ->
        when (result) {
            is ApiResult.Success ->
                LoginAttempt.Success(result.data.userId).also {
                    setAuthInfo(result.data)
                }
            is ApiResult.Failure ->
                LoginAttempt.Failure(result.error)
        }
    }

    fun login(type: AuthType, code: String) = flow {
        emit(performLogin(type, code))
    }.flowOn(dispatcher)

    // TODO: Temp
    fun loginFake(type: AuthType, code: String) {
        setAuthInfo(
            AuthInfo(
                "testUer($type)",
                code,
                Clock.System.now(),
                "testRefresh",
                Clock.System.now()
            )
        )
    }
}
