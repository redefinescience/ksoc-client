package com.kotlineering.ksoc.client.android.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kotlineering.ksoc.client.domain.auth.AuthService
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val authService: AuthService
): ViewModel() {
    val userId = authService.userId.stateIn(
        viewModelScope, SharingStarted.Lazily, null
    )

    fun logout() = viewModelScope.launch {
        authService.logout().collect()
    }
}
