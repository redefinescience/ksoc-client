package com.kotlineering.ksoc.client.android.koin

import com.kotlineering.ksoc.client.android.MainActivityViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { MainActivityViewModel(get()) }
}
