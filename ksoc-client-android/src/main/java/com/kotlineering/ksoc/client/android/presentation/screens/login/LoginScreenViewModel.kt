package com.kotlineering.ksoc.client.android.presentation.screens.login

import androidx.lifecycle.ViewModel
import com.kotlineering.ksoc.client.auth.AuthService

class LoginScreenViewModel(
    private val authService: AuthService
) : ViewModel() {
    fun fakeLogin(type: AuthService.AuthType)  {
        authService.loginFake(type, "")
    }
}
