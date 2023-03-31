package com.kotlineering.ksoc.client.domain.user

import com.kotlineering.ksoc.client.domain.auth.AuthService
import kotlinx.coroutines.CoroutineDispatcher

class UserRepository(
    private val authService: AuthService,
    private val dispatcher: CoroutineDispatcher
) {

}
