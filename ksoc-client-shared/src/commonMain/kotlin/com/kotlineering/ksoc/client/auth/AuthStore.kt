package com.kotlineering.ksoc.client.auth

interface AuthStore {
    fun storeTokens(tokens: Tokens?)
    fun fetchTokens(): Tokens?

    fun fetchUser(): CurrentUser?
    fun storeUser(user: CurrentUser?)

    companion object {
        const val KEY_BEARER_TOKEN = "KSOC_AUTH_BEARER_TOKEN"
        const val KEY_BEARER_EXPIRY = "KSOC_AUTH_BEARER_EXPIRY"
        const val KEY_REFRESH_TOKEN = "KSOC_AUTH_REFRESH_TOKEN"
        const val KEY_REFRESH_EXPIRY = "KSOC_AUTH_REFRESH_EXPIRY"

        const val KEY_CURRENT_USER_ID = "KSOC_AUTH_CURRENT_USER_ID"
    }
}
