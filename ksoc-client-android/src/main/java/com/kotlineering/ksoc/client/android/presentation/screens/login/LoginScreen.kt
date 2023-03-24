package com.kotlineering.ksoc.client.android.presentation.screens.login

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.lifecycle.ViewModel
import com.kotlineering.ksoc.client.auth.AuthRepository
import org.koin.androidx.compose.koinViewModel

class LoginScreenViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    init {
        Log.d("Test", "LoginScreenViewModel Created")
    }

    fun login(type: AuthRepository.AuthType) = authRepository.login(type)

    override fun onCleared() {
        Log.d("Test", "LoginScreenViewModel Cleared")
    }
}

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginScreenViewModel = koinViewModel()
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column {
            Text("Login")
            Text("")
            ClickableText(text = AnnotatedString("Log In - Microsoft"), onClick = {
                viewModel.login(AuthRepository.AuthType.Microsoft)
            })
            Text("")
            ClickableText(text = AnnotatedString("Log In - Google"), onClick = {
                viewModel.login(AuthRepository.AuthType.Google)
            })
            Text("")
            ClickableText(text = AnnotatedString("Log In - Apple"), onClick = {
                viewModel.login(AuthRepository.AuthType.Apple)
            })
            Text("")
            ClickableText(text = AnnotatedString("Log In - Facebook"), onClick = {
                viewModel.login(AuthRepository.AuthType.Facebook)
            })
        }
    }
}
