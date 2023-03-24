package com.kotlineering.ksoc.client.android

import android.app.Application
import com.kotlineering.ksoc.client.android.koin.viewModelModule
import com.kotlineering.ksoc.client.koin.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class KsocApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidLogger()
            androidContext(this@KsocApplication)
            modules(viewModelModule)
        }
    }
}
