package com.kotlineering.ksoc.client.android.presentation.navigation

import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.Navigator
import androidx.navigation.navOptions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class KsocNavigator {

    data class NavRequest(
        val route: String,
        val navOptions: NavOptions? = null,
        val navigatorExtras: Navigator.Extras? = null
    )

    private val _navTarget = MutableSharedFlow<NavRequest>(extraBufferCapacity = 2)
    val navTarget: Flow<NavRequest> = _navTarget.asSharedFlow()

    fun navigate(route: String, builder: NavOptionsBuilder.() -> Unit) =
        navigate(route, navOptions(builder), null)

    fun navigate(
        route: String,
        navOptions: NavOptions? = null,
        navigatorExtras: Navigator.Extras? = null
    ) = _navTarget.tryEmit(NavRequest(route, navOptions, navigatorExtras))
}
