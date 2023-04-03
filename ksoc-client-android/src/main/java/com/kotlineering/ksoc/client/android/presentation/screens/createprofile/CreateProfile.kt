package com.kotlineering.ksoc.client.android.presentation.screens.createprofile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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

class CreateProfileViewModel(
    private val authService: AuthService,
) : ViewModel() {
    val userId = authService.userId.stateIn(
        viewModelScope, SharingStarted.Lazily, null
    )

    // TODO: Custom state for idle/busy/ok/fail - Common Template - ServiceResultConverter

    fun createProfile(userInfo: UserInfo) = viewModelScope.launch {
        authService.updateProfile(userInfo).collect {
            // Do something with result
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
        CreateProfileScreen(id, viewModel::logout) { s, e ->
            viewModel.createProfile(UserInfo(id, s, e, null))
        }
    }
}

@Composable
fun CreateProfileScreen(
    userId: String,
    logout: () -> Unit,
    onCreateProfile: (screenName: String, email: String) -> Unit,
) {
    var screenName by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column {
            Text("Create Profile")
            Text("")
            OutlinedTextField(
                value = screenName,
                onValueChange = { screenName = it },
                label = { Text("Screen Name") }
            )
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") }
            )
            Text("")
            ClickableText(text = AnnotatedString("Create Profile"), onClick = {
                onCreateProfile(screenName, email)
            })
            Text("")
            ClickableText(text = AnnotatedString("Log Out"), onClick = {
                logout()
            })
        }
    }
}
