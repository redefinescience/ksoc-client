package com.kotlineering.ksoc.client.domain.auth

import com.kotlineering.ksoc.client.domain.ServiceResult
import com.kotlineering.ksoc.client.domain.user.UserInfo
import com.kotlineering.ksoc.client.HttpClientManager
import com.kotlineering.ksoc.client.remote.ApiResult
import com.kotlineering.ksoc.client.remote.auth.AuthApi
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.RefreshTokensParams
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.datetime.Clock

class AuthRepository(
    private val authStore: AuthStore,
    private val httpClientManager: HttpClientManager,
    private val authApi: AuthApi
) {
    internal val authInfo = MutableStateFlow(authStore.fetchAuthInfo())

    internal fun setAuthInfo(
        authInfo: AuthInfo?
    ) = authStore.storeAuthInfo(authInfo).also {
        httpClientManager.takeIf { it.isAuthSet }?.setAuth(authInfo) { performAutoRefresh(it) }
        this.authInfo.value = authInfo
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
                requireNotNull(authInfo.value) {
                    "AuthInfo should not be null when performing refresh."
                }.let { old ->
                    authInfo.value = AuthInfo(
                        old.userId,
                        tempToken,
                        tempExpiry,
                        tempRefresh,
                        tempRefreshExpiry,
                        old.userInfo
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
                Clock.System.now(),
                old.userInfo
            )
        } else {
            null
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

    internal fun performLogout() {
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
