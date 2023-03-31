package com.kotlineering.ksoc.client.android.koin

import com.kotlineering.ksoc.client.android.presentation.KsocAppContainerViewModel
import com.kotlineering.ksoc.client.android.presentation.screens.createprofile.CreateProfileViewModel
import com.kotlineering.ksoc.client.android.presentation.screens.home.HomeScreenViewModel
import com.kotlineering.ksoc.client.android.presentation.screens.login.LoginScreenViewModel
import com.kotlineering.ksoc.client.android.presentation.screens.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { KsocAppContainerViewModel(get()) }
    viewModel { LoginScreenViewModel(get()) }
    viewModel { HomeScreenViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { CreateProfileViewModel(get()) }
}
