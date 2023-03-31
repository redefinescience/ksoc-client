package com.kotlineering.ksoc.client.domain.auth

import com.kotlineering.ksoc.client.domain.ServiceResult
import com.kotlineering.ksoc.client.remote.ApiResult
import com.kotlineering.ksoc.client.domain.user.UserInfo

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock

class AuthService(
    private val authRepository: AuthRepository,
    private val dispatcher: CoroutineDispatcher
) {
    enum class AuthType {
        Microsoft, Google, Apple, Facebook,
    }

    private fun authStateFromAuthInfo(authInfo: AuthInfo?) = when {
        authInfo == null -> AuthState.NoUser
        authInfo.userInfo == null -> AuthState.NoProfile(authInfo.userId)
        else -> AuthState.LoggedIn(authInfo.userInfo)
    }

    val currentAuthState get() = authStateFromAuthInfo(authRepository.authInfo.value)

    val authState = authRepository.authInfo.map {
        authStateFromAuthInfo(it)
    }.distinctUntilChanged()
    val userId = authRepository.authInfo.map { it?.userId }
    val userInfo = authRepository.authInfo.map { it?.userInfo }

    // To be used during activity onCreate()
    // Conditionally run this (based on expiry etc..),
    // Do not set content until this is complete
    fun refreshToken() = flow {
        emit(authRepository.performManualRefresh())
    }.flowOn(dispatcher)

    fun logout() = flow {
        authRepository.performLogout()
        emit(ServiceResult.Success(null))
    }.flowOn(dispatcher)



    fun login(type: AuthType, code: String) = flow {
        emit(
            when (val result = authRepository.performLogin(type, code)) {
                is ApiResult.Success -> ServiceResult.Success(result.data.userId)
                is ApiResult.Failure -> ServiceResult.Failure("Login Failure")
            }
        )
    }.flowOn(dispatcher)

    fun updateProfile(
        userInfo: UserInfo
    ) = flow {
        emit(
            when (val result = authRepository.performUpdateProfile(userInfo)) {
                is ApiResult.Success -> ServiceResult.Success(result.data)
                is ApiResult.Failure -> ServiceResult.Failure("Failed to update user profile")
            }
        )
    }.flowOn(dispatcher)

    // TODO: Temp
    fun loginFake(type: AuthType, code: String) {
        authRepository.setAuthInfo(
            AuthInfo(
                "testUer($type)",
                code,
                Clock.System.now(),
                "testRefresh",
                Clock.System.now(),
                null
            )
        )
    }
}
