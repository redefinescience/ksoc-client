package com.kotlineering.ksoc.client.android.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.navigation.compose.rememberNavController
import com.kotlineering.ksoc.client.android.presentation.navigation.KsocNavHost
import com.kotlineering.ksoc.client.android.presentation.navigation.KsocNavigator
import com.kotlineering.ksoc.client.android.presentation.navigation.NavTarget
import com.kotlineering.ksoc.client.auth.AuthRepository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.compose.koinViewModel

class KsocAppContainerViewModel(
    authRepository: AuthRepository
) : ViewModel() {
    val navigator = KsocNavigator()

    val authInfo = authRepository.authInfoFlow
    var wasLoggedIn = false
}

@Composable
fun KsocAppContainer(
    modifier: Modifier = Modifier,
    viewModel: KsocAppContainerViewModel = koinViewModel()
) {
    val navController = rememberNavController()
    LaunchedEffect("navigator") {
        viewModel.navigator.navTarget.onEach {
            navController.navigate(it.target.route, it.navOptions, it.navigatorExtras)
        }.launchIn(this)
    }

    LaunchedEffect("authState") {
        viewModel.authInfo.onEach { authState ->
            if (authState == null) {
                viewModel.wasLoggedIn = false
                viewModel.navigator.navTo(NavTarget.Login) {
                    popUpTo(NavTarget.Home.route) { inclusive = true }
                }
            } else if (!viewModel.wasLoggedIn) {
                viewModel.wasLoggedIn = true
                viewModel.navigator.navTo(NavTarget.Home) {
                    popUpTo(NavTarget.Login.route) { inclusive = true }
                }
            }
        }.launchIn(this)
    }

    KsocNavHost(viewModel.navigator, navController, modifier)
}
