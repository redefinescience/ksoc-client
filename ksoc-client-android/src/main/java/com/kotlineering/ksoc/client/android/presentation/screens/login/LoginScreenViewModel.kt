package com.kotlineering.ksoc.client.android.presentation.screens.login

import androidx.lifecycle.ViewModel
import com.kotlineering.ksoc.client.auth.AuthRepository

class LoginScreenViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {
    fun fakeLogin(type: AuthRepository.AuthType)  {
        authRepository.authInfo = authRepository.loginFake(type, "")
    }
}
