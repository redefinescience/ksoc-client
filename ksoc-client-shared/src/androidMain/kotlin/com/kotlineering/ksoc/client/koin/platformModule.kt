package com.kotlineering.ksoc.client.koin

import android.content.Context
import com.kotlineering.ksoc.client.auth.AndroidAuthStore
import com.kotlineering.ksoc.client.auth.AuthStore
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual fun platformModule() = module {
    single<AuthStore> {
        AndroidAuthStore(
            androidContext().getSharedPreferences("kotlineering-ksoc", Context.MODE_PRIVATE)
        )
    }
}
