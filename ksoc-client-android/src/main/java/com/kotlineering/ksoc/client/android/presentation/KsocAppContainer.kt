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
import com.kotlineering.ksoc.client.auth.AuthService
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.compose.koinViewModel

class KsocAppContainerViewModel(
    authService: AuthService
) : ViewModel() {
    val navigator = KsocNavigator()

    val authInfo = authService.authInfo
    var wasLoggedIn = false
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
        viewModel.authInfo.onEach { authState ->
            if (authState == null) {
                viewModel.wasLoggedIn = false
                viewModel.navigator.navigate(RootNavTarget.Login.route) {
                    popUpTo(navController.graph.findStartDestination().id) { inclusive = true }
                }
            } else if (!viewModel.wasLoggedIn) {
                // TODO: revisit wasLoggedIn logic- won't work with createprofile/home
                viewModel.wasLoggedIn = true
                viewModel.navigator.navigate(
                    when (authState.userInfo) {
                        null -> RootNavTarget.CreateProfile.route
                        else -> RootNavTarget.Home.route
                    }
                ) {
                    popUpTo(navController.graph.findStartDestination().id) { inclusive = true }
                }
            }
        }.launchIn(this)
    }

    KsocNavHost(viewModel.navigator, navController, modifier)
}
