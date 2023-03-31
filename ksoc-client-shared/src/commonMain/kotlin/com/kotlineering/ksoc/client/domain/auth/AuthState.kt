package com.kotlineering.ksoc.client.domain.auth

import com.kotlineering.ksoc.client.domain.user.UserInfo

sealed interface AuthState {
    object NoUser : AuthState
    data class NoProfile(val userId: String) : AuthState
    data class LoggedIn(val userInfo: UserInfo) : AuthState
}
