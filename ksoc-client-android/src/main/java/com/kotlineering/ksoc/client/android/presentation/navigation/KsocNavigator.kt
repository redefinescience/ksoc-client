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
        val target: NavTarget,
        val navOptions: NavOptions? = null,
        val navigatorExtras: Navigator.Extras? = null
    )

    private val _navTarget = MutableSharedFlow<NavRequest>()
    val navTarget: Flow<NavRequest> = _navTarget.asSharedFlow()

    suspend fun navTo(target: NavTarget, builder: NavOptionsBuilder.() -> Unit) =
        navTo(target, navOptions(builder), null)

    suspend fun navTo(
        target: NavTarget,
        navOptions: NavOptions? = null,
        navigatorExtras: Navigator.Extras? = null
    ) = _navTarget.emit(NavRequest(target, navOptions, navigatorExtras))
}
