package com.kotlineering.ksoc.client.auth

import android.content.SharedPreferences
import kotlinx.datetime.Instant

class AndroidAuthStore(
    private val prefs: SharedPreferences
) : AuthStore {
    override fun storeAuthInfo(authInfo: AuthInfo?) {
        prefs.edit().apply {
            authInfo?.apply {
                putString(AuthStore.KEY_BEARER_TOKEN, bearer)
                putLong(AuthStore.KEY_BEARER_EXPIRY, bearerExpiry.toEpochMilliseconds())
                putString(AuthStore.KEY_REFRESH_TOKEN, refresh)
                putLong(AuthStore.KEY_REFRESH_EXPIRY, refreshExpiry.toEpochMilliseconds())
                putString(AuthStore.KEY_CURRENT_USER_ID, userId)
            } ?: run {
                remove(AuthStore.KEY_BEARER_TOKEN)
                remove(AuthStore.KEY_BEARER_EXPIRY)
                remove(AuthStore.KEY_REFRESH_TOKEN)
                remove(AuthStore.KEY_REFRESH_EXPIRY)
                remove(AuthStore.KEY_CURRENT_USER_ID)
            }
            apply()
        }
    }

    override fun fetchAuthInfo() = prefs.getString(AuthStore.KEY_BEARER_TOKEN, null)?.let { b ->
        prefs.getLong(AuthStore.KEY_BEARER_EXPIRY, 0).takeIf { it > 0 }?.let { bExp ->
            prefs.getString(AuthStore.KEY_REFRESH_TOKEN, null)?.let { r ->
                prefs.getLong(AuthStore.KEY_REFRESH_EXPIRY, 0).takeIf { it > 0 }?.let { rExp ->
                    prefs.getString(AuthStore.KEY_CURRENT_USER_ID, null)?.let { uId ->
                        AuthInfo(
                            uId,
                            b, Instant.fromEpochMilliseconds(bExp),
                            r, Instant.fromEpochMilliseconds(rExp),
                        )
                    }
                }
            }
        }
    }
}
