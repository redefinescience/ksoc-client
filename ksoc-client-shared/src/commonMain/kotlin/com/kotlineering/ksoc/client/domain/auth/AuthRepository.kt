package com.kotlineering.ksoc.client.domain.auth

import com.kotlineering.ksoc.client.domain.ServiceResult
import com.kotlineering.ksoc.client.domain.user.UserInfo
import com.kotlineering.ksoc.client.HttpClientManager
import com.kotlineering.ksoc.client.remote.ApiResult
import com.kotlineering.ksoc.client.remote.auth.AuthApi
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.RefreshTokensParams
import kotlinx.coroutines.flow.MutableStateFlow

class AuthRepository(
    private val authStore: AuthStore,
    private val httpClientManager: HttpClientManager,
    private val authApi: AuthApi
) {
    internal val authInfo = MutableStateFlow(
        onSetAuthInfo(authStore.fetchAuthInfo(), false)
    )

    private fun onSetAuthInfo(authInfo: AuthInfo?, store: Boolean = true) = authInfo.also {
        authStore.takeIf { store }?.storeAuthInfo(authInfo)
        httpClientManager.setAuth(authInfo) { performAutoRefresh(it) }
    }

    internal fun setAuthInfo(authInfo: AuthInfo?) {
        this.authInfo.value = onSetAuthInfo(authInfo)
    }

    private suspend fun performAutoRefresh(
        params: RefreshTokensParams
    ): BearerTokens? = params.oldTokens?.let { oldTokens ->
        authApi.refresh(
            oldTokens.accessToken, oldTokens.refreshToken, params.client
        ).let { result ->
            when (result) {
                is ApiResult.Success -> requireNotNull(authInfo.value) {
                    "AuthInfo should not be null when performing refresh."
                }.let { old ->
                    authInfo.value = result.data.copy(
                        userInfo = old.userInfo
                    ).also { authStore.storeAuthInfo(it) }
                    BearerTokens(result.data.bearer, result.data.refresh)
                }
                is ApiResult.Failure -> null.also {
                    // TODO: (need to test) Might need to store null, and save null to authInfo
                    // (ie: skip clearing auth in http client)
                    setAuthInfo(null)
                }
            }
        }
    }

    private suspend fun performRefresh(
        old: AuthInfo
    ) = authApi.refresh(old.bearer, old.refresh).let { result ->
        when (result) {
            is ApiResult.Success -> result.data.copy(userInfo = old.userInfo)
            is ApiResult.Failure -> null
        }
    }.also {
        setAuthInfo(it)
    }

    internal suspend fun performManualRefresh() =
        authInfo.value?.let { old ->
            performRefresh(old)?.let {
                ServiceResult.Success(old.userId)
            } ?: ServiceResult.Failure("Failed to refresh token")
        } ?: ServiceResult.Failure("No refresh token")

    internal suspend fun performLogin(
        type: AuthService.AuthType, code: String
    ) = authApi.login(
        type, code
    ).also { result ->
        if (result is ApiResult.Success) {
            setAuthInfo(result.data)
        }
        when (result) {
            is ApiResult.Success ->
                ServiceResult.Success(result.data.userId).also {
                    setAuthInfo(result.data)
                }
            is ApiResult.Failure ->
                ServiceResult.Failure(result.error)
        }
    }

    internal suspend fun performLogout() {
        authInfo.value?.let { authInfo ->
            authApi.logout(authInfo.bearer, authInfo.refresh)
        }
        setAuthInfo(null)
    }

    internal suspend fun performUpdateProfile(
        userInfo: UserInfo
    ): ApiResult<UserInfo> = authApi.updateUserProfile(
        userInfo
    ).also { result ->
        if (result is ApiResult.Success) {
            setAuthInfo(
                requireNotNull(authInfo.value).copy(
                    userInfo = result.data
                )
            )
        }
    }
}
