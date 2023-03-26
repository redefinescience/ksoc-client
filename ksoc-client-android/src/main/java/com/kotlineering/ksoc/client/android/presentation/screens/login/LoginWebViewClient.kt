package com.kotlineering.ksoc.client.android.presentation.screens.login

import android.webkit.WebViewClient
import com.kotlineering.ksoc.client.auth.AuthRepository

abstract class LoginWebViewClient : WebViewClient() {
    var onSuccess: ((type: AuthRepository.AuthType, token: String) -> Any?)? = null
    abstract val loginUrl: String
}
