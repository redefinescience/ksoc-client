package com.kotlineering.ksoc.client.android.presentation.navigation

enum class NavTarget(val route: String) {
    Login("login"),
    Home("home");

    override fun toString(): String = route
}
