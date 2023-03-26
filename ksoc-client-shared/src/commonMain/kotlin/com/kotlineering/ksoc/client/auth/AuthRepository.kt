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
