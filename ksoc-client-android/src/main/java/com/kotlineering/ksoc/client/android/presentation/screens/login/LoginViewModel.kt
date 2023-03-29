package com.kotlineering.ksoc.client.android.presentation.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlineering.ksoc.client.auth.AuthRepository
import com.kotlineering.ksoc.client.auth.LoginAttempt
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val mutableLoginState = MutableStateFlow<LoginAttempt?>(null)
    val loginState = mutableLoginState.asStateFlow()

    fun login(type: AuthRepository.AuthType, code: String) = viewModelScope.launch {
        authRepository.login(type, code).collect {
            mutableLoginState.emit(it)
            if (it is LoginAttempt.Success) {
                authRepository.authInfo = it.authInfo
            }
        }
    }

    fun retry() = viewModelScope.launch {
        mutableLoginState.emit(null)
    }
}
