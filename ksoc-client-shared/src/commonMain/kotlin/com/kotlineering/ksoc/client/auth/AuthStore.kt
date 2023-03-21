package com.kotlineering.ksoc.client.auth

interface AuthStore {
    fun storeTokens(tokens: Tokens?)
    fun fetchTokens(): Tokens?

    fun fetchUser(): CurrentUser?
    fun storeUser(user: CurrentUser?)
}
