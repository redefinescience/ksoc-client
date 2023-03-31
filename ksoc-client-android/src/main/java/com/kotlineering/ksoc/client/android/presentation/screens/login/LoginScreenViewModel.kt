package com.kotlineering.ksoc.client.android.presentation.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlineering.ksoc.client.domain.auth.AuthService
import kotlinx.coroutines.launch

class LoginScreenViewModel(
    private val authService: AuthService
) : ViewModel() {
    fun fakeLogin(type: AuthService.AuthType) = viewModelScope.launch {
        authService.loginFake(type, "")
    }
}
