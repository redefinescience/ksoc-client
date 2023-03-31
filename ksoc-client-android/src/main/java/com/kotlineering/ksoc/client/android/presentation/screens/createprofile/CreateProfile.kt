package com.kotlineering.ksoc.client.android.presentation.screens.createprofile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.kotlineering.ksoc.client.android.presentation.navigation.KsocNavigator
import com.kotlineering.ksoc.client.auth.AuthService
import com.kotlineering.ksoc.client.user.UserInfo
import com.kotlineering.ksoc.client.user.UserRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

// TODO: Move Me
class CreateProfileViewModel(
    val authService: AuthService,
    val userRepository: UserRepository
) : ViewModel() {
    val userId = authService.authInfo.map {
        it?.userId
    }.stateIn(viewModelScope, SharingStarted.Lazily, null)

    fun createProfile(userInfo: UserInfo) = viewModelScope.launch {
        userRepository.updateProfile(userInfo)
    }
}

@Composable
fun CreateProfileRoute(
    navigator: KsocNavigator,
    viewModel: CreateProfileViewModel = koinViewModel()
) {
    val userId by viewModel.userId.collectAsStateWithLifecycle()

    CreateProfileScreen(requireNotNull(userId)) {
        viewModel.createProfile(it)
    }
}

@Composable
fun CreateProfileScreen(
    userId: String,
    onCreateProfile: (UserInfo) -> Unit
) {

}