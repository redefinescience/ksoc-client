package com.kotlineering.ksoc.client.android.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kotlineering.ksoc.client.android.presentation.screens.home.HomeScreen
import com.kotlineering.ksoc.client.android.presentation.screens.login.LoginScreen

@Composable
fun KsocNavHost(
    navigator: KsocNavigator,
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = NavTarget.Home.route,
        modifier = modifier
    ) {
        composable(NavTarget.Login.route) {
            LoginScreen()
        }
        composable(NavTarget.Home.route) {
            HomeScreen(navigator)
        }
    }
}
