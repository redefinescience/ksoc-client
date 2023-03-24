package com.kotlineering.ksoc.client.auth

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.datetime.Clock

class AuthRepository(
    private val authStore: AuthStore
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
        set(t) = authStore.storeAuthInfo(t).also { mutableAuthInfoFlow.value = t }

    fun logout() {
        authInfo = null
    }

    fun login(type: AuthType) {
        // TODO: Stub
        authInfo = AuthInfo(
            "testToken",
            Clock.System.now(),
            "testRefresh",
            Clock.System.now(),
            "testUer(${type.toString()})"
        )
    }
}
