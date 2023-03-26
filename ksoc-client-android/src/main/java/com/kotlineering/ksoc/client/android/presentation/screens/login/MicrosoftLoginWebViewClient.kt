package com.kotlineering.ksoc.client.android.presentation.screens.login

import android.net.Uri
import android.webkit.WebResourceRequest
import android.webkit.WebView
import com.kotlineering.ksoc.client.auth.AuthRepository

class MicrosoftLoginWebViewClient : LoginWebViewClient() {
    // TODO: This should come from backend
    override val loginUrl: String
        get() = "https://login.microsoftonline.com/consumers/oauth2/v2.0/authorize?" +
            "client_id=9136b230-9139-446a-850d-9a72f7ed6a40" +
            "&response_type=id_token" +
            "&redirect_uri=http%3A%2F%2Flocalhost%3A5000%2Flogin%2Fms" +
            "&response_mode=fragment" +
            "&scope=openid" +
            "&state=12345" +
            "&nonce=678910"

    override fun shouldOverrideUrlLoading(
        view: WebView?,
        request: WebResourceRequest?
    ): Boolean = request?.url?.takeIf {
        // TODO: This condition should be improved, and include the 'state' query param validation
        it.host == "localhost" && it.port == 5000
    }?.let { uri ->
        Uri.parse(
            uri.toString().replace("#", "?")
        ).getQueryParameter("id_token")?.let { id_token ->
            onSuccess?.invoke(AuthRepository.AuthType.Microsoft, id_token)
            true
        }
    } ?: false
}
