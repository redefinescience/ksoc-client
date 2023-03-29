package com.kotlineering.ksoc.client.android.presentation.screens.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.navigation.NavController
import com.kotlineering.ksoc.client.auth.AuthRepository
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    navController: NavController,
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
                navController.navigate(LoginNavTarget.Microsoft.route)
            })
            Text("")
            ClickableText(text = AnnotatedString("Log In - Google"), onClick = {
                viewModel.fakeLogin(AuthRepository.AuthType.Google)
            })
            Text("")
            ClickableText(text = AnnotatedString("Log In - Apple"), onClick = {
                viewModel.fakeLogin(AuthRepository.AuthType.Apple)
            })
            Text("")
            ClickableText(text = AnnotatedString("Log In - Facebook"), onClick = {
                viewModel.fakeLogin(AuthRepository.AuthType.Facebook)
            })
        }
    }
}
