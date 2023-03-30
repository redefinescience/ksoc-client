package com.kotlineering.ksoc.client.android.presentation.screens.login

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.kotlineering.ksoc.client.android.presentation.navigation.KsocNavigator
import com.kotlineering.ksoc.client.android.presentation.screens.login.webview.LoginWebViewRoute
import com.kotlineering.ksoc.client.android.presentation.screens.login.webview.MicrosoftLoginWebViewClient

enum class LoginNavTarget(val route: String) {
    Root("root"),
    Microsoft("microsoft"),
    Google("google"),
    Apple("apple"),
    Facebook("facebook");

    override fun toString(): String = route
}

fun NavGraphBuilder.loginNavGraph(parentRoute: String, navigator: KsocNavigator) {
    navigation(LoginNavTarget.Root.route, parentRoute) {
        composable(LoginNavTarget.Root.route) {
            LoginScreenRoute(navigator)
        }
        composable(LoginNavTarget.Microsoft.route) {
            LoginWebViewRoute(MicrosoftLoginWebViewClient())
        }
    }
}
