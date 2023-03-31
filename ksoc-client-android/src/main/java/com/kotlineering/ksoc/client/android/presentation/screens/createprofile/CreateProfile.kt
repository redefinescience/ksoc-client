package com.kotlineering.ksoc.client.android.presentation.screens.createprofile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.kotlineering.ksoc.client.android.presentation.navigation.KsocNavigator
import com.kotlineering.ksoc.client.domain.auth.AuthService
import com.kotlineering.ksoc.client.domain.user.UserInfo
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

// TODO: Move Me
class CreateProfileViewModel(
    private val authService: AuthService,
) : ViewModel() {
    val userId = authService.userId.stateIn(
        viewModelScope, SharingStarted.Lazily, null
    )

    fun createProfile(userInfo: UserInfo) = viewModelScope.launch {
        authService.updateProfile(userInfo).collect {

        }
    }

    fun logout() = viewModelScope.launch {
        authService.logout().collect()
    }
}

@Composable
fun CreateProfileRoute(
    navigator: KsocNavigator,
    viewModel: CreateProfileViewModel = koinViewModel()
) {
    val userId by viewModel.userId.collectAsStateWithLifecycle()

    userId?.let { id ->
        CreateProfileScreen(id, viewModel::logout, viewModel::createProfile)
    }
}

@Composable
fun CreateProfileScreen(
    userId: String,
    logout: () -> Unit,
    onCreateProfile: (UserInfo) -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column {
            Text("TODO: Create Profile")
            Text("")
            ClickableText(text = AnnotatedString("Log Out"), onClick = {
                logout.invoke()
            })
        }
    }
}
