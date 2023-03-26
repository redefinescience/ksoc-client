package com.kotlineering.ksoc.client.android.presentation.screens.login

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import org.koin.androidx.compose.koinViewModel

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun LoginWebView(
    client: LoginWebViewClient,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = koinViewModel(),
) {
    AndroidView(
        factory = {
            WebView(it).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                webViewClient = client.apply {
                    onSuccess = { type, token ->
                        viewModel.authRepository.login(type, token)
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