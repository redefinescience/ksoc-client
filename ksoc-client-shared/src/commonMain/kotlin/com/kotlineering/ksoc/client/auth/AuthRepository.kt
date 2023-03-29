package com.kotlineering.ksoc.client.auth

import com.kotlineering.ksoc.client.http.HttpClientManager
import com.kotlineering.ksoc.client.remote.ApiResult
import com.kotlineering.ksoc.client.remote.AuthApi
import io.ktor.client.plugins.auth.providers.BearerTokens

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.datetime.Clock

sealed interface LoginAttempt {
    data class Success(val authInfo: AuthInfo) : LoginAttempt
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

    private val mutableAuthInfoFlow = MutableStateFlow(authStore.fetchAuthInfo())

    val authInfoFlow: Flow<AuthInfo?>
        get() = mutableAuthInfoFlow


    var authInfo: AuthInfo?
        get() = mutableAuthInfoFlow.value
        set(t) = authStore.storeAuthInfo(t).also {
            httpClientManager.takeIf { it.isAuthSet }?.setAuth(t) { refresh() }
            mutableAuthInfoFlow.value = t
        }

    // TODO: real refresh
    private fun refresh() = authInfo?.let { info ->
        BearerTokens(info.bearer, info.refresh).also {
            authInfo = info
        }
    }.also {
        if (it == null) authInfo = null
    }

    fun logout() {
        authInfo = null
        httpClientManager.setAuth(null, null)
    }

    fun login(type: AuthType, code: String) = flow {
        authApi.login(
            type, code
        ).let { result ->
            emit(
                when (result) {
                    is ApiResult.Success ->
                        LoginAttempt.Success(result.data)
                    is ApiResult.Failure ->
                        LoginAttempt.Failure(result.error)
                }
            )
        }
    }.flowOn(dispatcher)

    // TODO: Temp
    fun loginFake(type: AuthType, code: String) = AuthInfo(
        code,
        Clock.System.now(),
        "testRefresh",
        Clock.System.now(),
        "testUer($type)"
    )
}
