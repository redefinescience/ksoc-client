package com.kotlineering.ksoc.client.android.presentation.screens.login

import androidx.lifecycle.ViewModel
import com.kotlineering.ksoc.client.auth.AuthRepository

class LoginViewModel(
    val authRepository: AuthRepository
) : ViewModel()