package com.kotlineering.ksoc.client.android.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlineering.ksoc.client.auth.AuthService
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HomeScreenViewModel(
    private val authService: AuthService
): ViewModel() {
    val userId = authService.authInfo.map {
        it?.userId
    }.stateIn(viewModelScope, SharingStarted.Lazily, null)

    fun logout() = authService.logout()
}