package com.kotlineering.ksoc.client.auth

import android.content.SharedPreferences
import kotlinx.datetime.Instant

class AndroidAuthStore(
    private val prefs: SharedPreferences
) : AuthStore {
    override fun storeTokens(tokens: Tokens?) {
        prefs.edit().apply {
            tokens?.apply {
                putString(AuthStore.KEY_BEARER_TOKEN, bearer)
                putLong(AuthStore.KEY_REFRESH_EXPIRY, bearerExpiry.toEpochMilliseconds())
                putString(AuthStore.KEY_REFRESH_TOKEN, refresh)
                putLong(AuthStore.KEY_BEARER_TOKEN, refreshExpiry.toEpochMilliseconds())
            } ?: run {
                remove(AuthStore.KEY_BEARER_TOKEN)
                remove(AuthStore.KEY_BEARER_EXPIRY)
                remove(AuthStore.KEY_REFRESH_TOKEN)
                remove(AuthStore.KEY_REFRESH_EXPIRY)
            }
            apply()
        }
    }

    override fun fetchTokens() = prefs.getString(AuthStore.KEY_BEARER_TOKEN, null)?.let { b ->
        prefs.getLong(AuthStore.KEY_BEARER_EXPIRY, 0).takeIf { it > 0 }?.let { bExp ->
            prefs.getString(AuthStore.KEY_REFRESH_TOKEN, null)?.let { r ->
                prefs.getLong(AuthStore.KEY_REFRESH_EXPIRY, 0).takeIf { it > 0 }?.let { rExp ->
                    Tokens(
                        b, Instant.fromEpochMilliseconds(bExp),
                        r, Instant.fromEpochMilliseconds(rExp),
                    )
                }
            }
        }
    }

    override fun fetchUser() = prefs.getString(AuthStore.KEY_CURRENT_USER_ID, null)?.let { id ->
        CurrentUser(id)
    }

    override fun storeUser(user: CurrentUser?) {
        prefs.edit().apply {
            user?.apply {
                putString(AuthStore.KEY_CURRENT_USER_ID, id)
            } ?: run {
                remove(AuthStore.KEY_CURRENT_USER_ID)
            }
            apply()
        }
    }
}
