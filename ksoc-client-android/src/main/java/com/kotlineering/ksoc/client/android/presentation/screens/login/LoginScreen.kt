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
import com.kotlineering.ksoc.client.android.presentation.navigation.KsocNavigator
import com.kotlineering.ksoc.client.auth.AuthService
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreenRoute(
    navigator: KsocNavigator,
    viewModel: LoginScreenViewModel = koinViewModel()
) = LoginScreen(navigator, viewModel::fakeLogin)

@Composable
fun LoginScreen(
    navigator: KsocNavigator,
    onFakeLogin: (AuthService.AuthType) -> Unit = {}
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column {
            Text("Login")
            Text("")
            ClickableText(text = AnnotatedString("Log In - Microsoft"), onClick = {
                navigator.navigate(LoginNavTarget.Microsoft.route)
            })
            Text("")
            ClickableText(text = AnnotatedString("Log In - Google"), onClick = {
                onFakeLogin(AuthService.AuthType.Google)
            })
            Text("")
            ClickableText(text = AnnotatedString("Log In - Apple"), onClick = {
                onFakeLogin(AuthService.AuthType.Apple)
            })
            Text("")
            ClickableText(text = AnnotatedString("Log In - Facebook"), onClick = {
                onFakeLogin(AuthService.AuthType.Facebook)
            })
        }
    }
}
