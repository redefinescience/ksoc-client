package com.kotlineering.ksoc.client.android.presentation.navigation

enum class RootNavTarget(val route: String) {
    Login("login"),
    CreateProfile("create_profile"),
    Home("home");

    override fun toString(): String = route
}
