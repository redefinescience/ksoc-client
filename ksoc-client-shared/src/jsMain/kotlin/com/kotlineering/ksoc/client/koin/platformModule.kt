package com.kotlineering.ksoc.client.koin

import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

actual fun platformModule() = module {
    single { Dispatchers.Default }
}
