package com.kotlineering.ksoc.client.android.presentation.screens.login.webview

import android.webkit.WebViewClient
import com.kotlineering.ksoc.client.auth.AuthRepository

abstract class LoginWebViewClient : WebViewClient() {
    var onSuccess: ((type: AuthRepository.AuthType, code: String) -> Any)? = null
    abstract val loginUrl: String
}
