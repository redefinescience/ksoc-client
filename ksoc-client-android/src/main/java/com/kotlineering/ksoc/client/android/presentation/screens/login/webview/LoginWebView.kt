package com.kotlineering.ksoc.client.android.presentation.screens.login.webview

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebView
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import com.kotlineering.ksoc.client.android.presentation.screens.login.LoginViewModel
import com.kotlineering.ksoc.client.auth.AuthService
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginWebViewRoute(
    client: LoginWebViewClient,
    viewModel: LoginViewModel = koinViewModel(),
) = LoginWebView(client, viewModel::login)

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun LoginWebView(
    client: LoginWebViewClient,
    onLogin: (type: AuthService.AuthType, code: String) -> Unit
) {
    AndroidView(
        factory = {
            WebView(it).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                webViewClient = client.apply {
                    onSuccess = { type, code ->
                        onLogin(type, code)
                    }
                }
                settings.apply {
                    javaScriptEnabled = true
                }

            }
        },
        update = { webView ->
            CookieManager.getInstance().removeAllCookies {
                webView.loadUrl(client.loginUrl)
            }
        }
    )
}