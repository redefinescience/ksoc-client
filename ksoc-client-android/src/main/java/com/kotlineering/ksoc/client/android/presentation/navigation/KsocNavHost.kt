package com.kotlineering.ksoc.client.android.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kotlineering.ksoc.client.android.presentation.screens.createprofile.CreateProfileRoute
import com.kotlineering.ksoc.client.android.presentation.screens.home.HomeScreenRoute
import com.kotlineering.ksoc.client.android.presentation.screens.login.loginNavGraph

@Composable
fun KsocNavHost(
    navigator: KsocNavigator,
    navController: NavHostController,
    startRoute: String,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = startRoute,
        modifier = modifier
    ) {
        loginNavGraph(RootNavTarget.Login.route, navigator)
        composable(RootNavTarget.CreateProfile.route) {
            CreateProfileRoute(navigator)
        }
        composable(RootNavTarget.Home.route) {
            HomeScreenRoute(navigator)
        }
    }
}
