package com.kotlineering.ksoc.client.auth

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class AuthRepository(
    private val authStore: AuthStore
) {
    private val mutableAuthInfoFlow = MutableStateFlow(authStore.fetchAuthInfo())

    val authInfoFlow: Flow<AuthInfo?>
        get() = mutableAuthInfoFlow

    var authInfo: AuthInfo?
        get() = mutableAuthInfoFlow.value
        set(t) = authStore.storeAuthInfo(t).also { mutableAuthInfoFlow.value = t }
}
