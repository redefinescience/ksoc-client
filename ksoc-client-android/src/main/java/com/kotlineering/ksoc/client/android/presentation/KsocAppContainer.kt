package com.kotlineering.ksoc.client.android.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.kotlineering.ksoc.client.android.presentation.navigation.KsocNavHost
import com.kotlineering.ksoc.client.android.presentation.navigation.KsocNavigator
import com.kotlineering.ksoc.client.android.presentation.navigation.RootNavTarget
import com.kotlineering.ksoc.client.domain.auth.AuthService
import com.kotlineering.ksoc.client.domain.auth.AuthState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.compose.koinViewModel

class KsocAppContainerViewModel(
    authService: AuthService
) : ViewModel() {
    val navigator = KsocNavigator()
    val authState = authService.authState
    val initialAuthState = authService.currentAuthState
}

private fun routeFromAuthState(
    state: AuthState
) = when (state) {
    is AuthState.NoUser -> RootNavTarget.Login.route
    is AuthState.NoProfile -> RootNavTarget.CreateProfile.route
    is AuthState.LoggedIn -> RootNavTarget.Home.route
}

@Composable
fun KsocAppContainer(
    modifier: Modifier = Modifier,
    viewModel: KsocAppContainerViewModel = koinViewModel()
) {
    val navController = rememberNavController()

    // Listen for, and execute navigation requests
    LaunchedEffect("navigator") {
        viewModel.navigator.navTarget.onEach {
            navController.navigate(it.route, it.navOptions, it.navigatorExtras)
        }.launchIn(this)
    }

    // Listen for changes in authentication, force navigation to login or home appropriately
    LaunchedEffect("authState") {
        viewModel.authState.onEach { authState ->
            navController.graph.findStartDestination().let { currentStart ->
                routeFromAuthState(authState).let { newStartRoute ->
                    viewModel.navigator.navigate(newStartRoute) {
                        navController.graph.setStartDestination(newStartRoute)
                        popUpTo(currentStart.id) {
                            inclusive = true
                        }
                    }
                }
            }
        }.launchIn(this)
    }

    KsocNavHost(
        viewModel.navigator,
        navController,
        routeFromAuthState(viewModel.initialAuthState),
        modifier
    )
}
