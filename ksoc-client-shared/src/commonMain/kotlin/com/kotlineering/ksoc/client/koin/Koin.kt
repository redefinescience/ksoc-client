package com.kotlineering.ksoc.client.koin

import com.kotlineering.ksoc.client.remote.AuthApi
import com.kotlineering.ksoc.client.auth.AuthService
import com.kotlineering.ksoc.client.http.HttpClientManager
import com.kotlineering.ksoc.client.user.UserRepository
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
    single { HttpClientManager(get()) }

    single { get<HttpClientManager>().client }
    single { AuthService(get(), get(), get(), get()) }

    single { AuthApi(get()) }

    single { UserRepository(get(), get()) }
}
