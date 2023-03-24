package com.kotlineering.ksoc.client.android.presentation.screens.home

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
import com.kotlineering.ksoc.client.Greeting
import com.kotlineering.ksoc.client.android.presentation.navigation.KsocNavigator
import com.kotlineering.ksoc.client.auth.AuthRepository
import org.koin.androidx.compose.koinViewModel

class HomeScreenViewModel(
    private val authRepository: AuthRepository
): ViewModel() {
    // TODO: Don't obtain user info from authInfo, obtain it from UserRepository (when it exists)
    val userId get() = authRepository.authInfo?.userId

    fun logout() = authRepository.logout()
}

@Composable
fun HomeScreen(
    navigator: KsocNavigator,
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel = koinViewModel()
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column {
            Text("${Greeting().greet()} - UserId: ${viewModel.userId}")
            Text("")
            ClickableText(text = AnnotatedString("Log Out"), onClick = { viewModel.logout() })
        }
    }
}
