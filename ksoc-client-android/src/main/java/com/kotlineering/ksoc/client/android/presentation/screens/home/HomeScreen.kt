package com.kotlineering.ksoc.client.android.presentation.screens.home

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
import com.kotlineering.ksoc.client.Greeting
import com.kotlineering.ksoc.client.android.presentation.navigation.KsocNavigator
import com.kotlineering.ksoc.client.domain.auth.AuthService
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

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

@Composable
fun HomeScreenRoute(
    navigator: KsocNavigator,
    viewModel: HomeScreenViewModel = koinViewModel()
) {
    val userId by viewModel.userId.collectAsStateWithLifecycle()

    HomeScreen(
        navigator,
        userId,
        viewModel::logout
    )
}

@Composable
fun HomeScreen(
    navigator: KsocNavigator,
    userId: String?,
    logout: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column {
            Text("${Greeting().greet()} - UserId: $userId")
            Text("")
            ClickableText(text = AnnotatedString("Log Out"), onClick = { logout() })
        }
    }
}
