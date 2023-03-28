package com.kotlineering.ksoc.client.koin

import com.kotlineering.ksoc.client.auth.AuthRepository
import com.kotlineering.ksoc.client.http.createHttpClient
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration? = null) = startKoin {
    appDeclaration?.invoke(this)
    modules(
        commonModule(),
        platformModule()
    )
}

fun commonModule() = module {
    single {
        AuthRepository(get(), get(), get())
    }

    single {
        createHttpClient(get())
    }
}
