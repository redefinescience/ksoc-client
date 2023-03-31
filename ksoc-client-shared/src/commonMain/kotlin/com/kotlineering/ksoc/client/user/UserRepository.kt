package com.kotlineering.ksoc.client.user

import com.kotlineering.ksoc.client.auth.AuthService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

@kotlinx.serialization.Serializable
data class UserInfo(
    val id: String,
    val email: String,
    val displayName: String,
    val image: String?
)

class UserRepository(
    private val authService: AuthService,
    private val dispatcher: CoroutineDispatcher
) {
    suspend fun updateProfile(
        userInfo: UserInfo
    ) = withContext(dispatcher) {
        // TODO!

        // Success, need to update auth info
        authService.setAuthInfo(
            requireNotNull(authService.authInfo.value).copy(
                userInfo = userInfo
            )
        )
    }
}
