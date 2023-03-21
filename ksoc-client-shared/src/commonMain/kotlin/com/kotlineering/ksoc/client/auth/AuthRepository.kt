package com.kotlineering.ksoc.client.auth

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class AuthRepository(
    private val authStore: AuthStore
) {
    private val mutableUser = MutableStateFlow(authStore.fetchUser())
    private val mutableTokens = MutableStateFlow(authStore.fetchTokens())

    val tokensFlow: Flow<Tokens?>
        get() = mutableTokens

    val userFlow: Flow<CurrentUser?>
        get() = mutableUser

    var user: CurrentUser?
        get() = mutableUser.value
        set(u) = authStore.storeUser(u).also { mutableUser.value = u }

    var tokens: Tokens?
        get() = mutableTokens.value
        set(t) = authStore.storeTokens(t).also { mutableTokens.value = t }
}
