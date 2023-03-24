package com.kotlineering.ksoc.client.android.presentation.screens.home

import androidx.lifecycle.ViewModel
import com.kotlineering.ksoc.client.auth.AuthRepository

class HomeScreenViewModel(
    private val authRepository: AuthRepository
): ViewModel() {
    // TODO: Don't obtain user info from authInfo, obtain it from UserRepository (when it exists)
    val userId get() = authRepository.authInfo?.userId

    fun logout() = authRepository.logout()
}