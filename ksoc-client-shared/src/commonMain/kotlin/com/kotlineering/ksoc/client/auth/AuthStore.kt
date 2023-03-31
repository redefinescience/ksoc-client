package com.kotlineering.ksoc.client.auth

interface AuthStore {
    fun storeAuthInfo(authInfo: AuthInfo?)
    fun fetchAuthInfo(): AuthInfo?

    companion object {
        const val KEY_BEARER_TOKEN = "KSOC_AUTH_BEARER_TOKEN"
        const val KEY_BEARER_EXPIRY = "KSOC_AUTH_BEARER_EXPIRY"
        const val KEY_REFRESH_TOKEN = "KSOC_AUTH_REFRESH_TOKEN"
        const val KEY_REFRESH_EXPIRY = "KSOC_AUTH_REFRESH_EXPIRY"
        const val KEY_CURRENT_USER_ID = "KSOC_AUTH_CURRENT_USER_ID"
        const val KEY_CURRENT_USER_EMAIL = "KSOC_AUTH_CURRENT_USER_EMAIL"
        const val KEY_CURRENT_USER_DISPLAYNAME = "KSOC_AUTH_CURRENT_USER_DISPLAYNAME"
        const val KEY_CURRENT_USER_IMAGE = "KSOC_AUTH_CURRENT_USER_IMAGE"
    }
}
