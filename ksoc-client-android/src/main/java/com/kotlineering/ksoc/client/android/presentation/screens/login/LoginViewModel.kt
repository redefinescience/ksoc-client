package com.kotlineering.ksoc.client.android.presentation.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlineering.ksoc.client.domain.auth.AuthService
import com.kotlineering.ksoc.client.domain.ServiceResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authService: AuthService
) : ViewModel() {

    // TODO: Custom state for idle/busy/ok/fail - Common Template - ServiceResultConverter
    private val mutableLoginState = MutableStateFlow<ServiceResult<String>?>(null)
    val loginState = mutableLoginState.asStateFlow()

    fun login(type: AuthService.AuthType, code: String) = viewModelScope.launch {
        authService.login(type, code).collect {
            mutableLoginState.emit(it)
        }
    }

    fun retry() = viewModelScope.launch {
        mutableLoginState.emit(null)
    }

    // For WIP
    fun fakeLogin(type: AuthService.AuthType) = viewModelScope.launch {
        authService.loginFake(type, "")
    }
}
