package com.kotlineering.ksoc.client.koin

import com.kotlineering.ksoc.client.remote.auth.AuthApi
import com.kotlineering.ksoc.client.domain.auth.AuthService
import com.kotlineering.ksoc.client.HttpClientManager
import com.kotlineering.ksoc.client.domain.auth.AuthRepository
import com.kotlineering.ksoc.client.domain.user.UserRepository
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

// TODO Split off service module & remote module
fun commonModule() = module {
    single { HttpClientManager(get()) }

    single { get<HttpClientManager>().client }

    single { AuthRepository(get(), get(), get())}
    single { AuthService(get(), get()) }

    single { AuthApi(get()) }

    single { UserRepository(get(), get()) }
}
