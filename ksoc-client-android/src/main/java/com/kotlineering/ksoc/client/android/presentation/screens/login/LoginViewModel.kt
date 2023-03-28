package com.kotlineering.ksoc.client.android.presentation.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlineering.ksoc.client.auth.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {
    fun login(type: AuthRepository.AuthType, code: String) = viewModelScope.launch(Dispatchers.IO) {
        authRepository.login2(type, code)
    }
}
