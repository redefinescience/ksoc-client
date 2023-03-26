package com.kotlineering.ksoc.client.android.presentation.screens.login

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.kotlineering.ksoc.client.android.presentation.screens.login.webview.LoginWebView
import com.kotlineering.ksoc.client.android.presentation.screens.login.webview.MicrosoftLoginWebViewClient

internal enum class LoginNavTarget(val route: String) {
    Root("root"),
    Microsoft("microsoft"),
    Google("google"),
    Apple("apple"),
    Facebook("facebook");

    override fun toString(): String = route
}

fun NavGraphBuilder.loginNavGraph(parentRoute: String, navController: NavController) {
    navigation(LoginNavTarget.Root.route, parentRoute) {
        composable(LoginNavTarget.Root.route) {
            LoginScreen(navController)
        }
        composable(LoginNavTarget.Microsoft.route) {
            LoginWebView(MicrosoftLoginWebViewClient())
        }
    }
}
