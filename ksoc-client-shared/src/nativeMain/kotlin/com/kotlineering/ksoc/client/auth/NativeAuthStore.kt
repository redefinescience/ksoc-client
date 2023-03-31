package com.kotlineering.ksoc.client.auth

import com.kotlineering.ksoc.client.domain.auth.AuthInfo
import com.kotlineering.ksoc.client.domain.auth.AuthStore

class NativeAuthStore: AuthStore {
    override fun storeAuthInfo(authInfo: AuthInfo?) {
        TODO("Not yet implemented")
    }

    override fun fetchAuthInfo(): AuthInfo? {
        TODO("Not yet implemented")
    }
}
