package com.kotlineering.ksoc.client.domain.auth

import android.content.SharedPreferences
import com.kotlineering.ksoc.client.domain.user.UserInfo
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
                userInfo?.email?.let {
                    putString(AuthStore.KEY_CURRENT_USER_EMAIL, it)
                } ?: remove(AuthStore.KEY_CURRENT_USER_EMAIL)
                userInfo?.displayName?.let {
                    putString(AuthStore.KEY_CURRENT_USER_DISPLAYNAME, it)
                } ?: remove(AuthStore.KEY_CURRENT_USER_DISPLAYNAME)
                userInfo?.image?.let {
                    putString(AuthStore.KEY_CURRENT_USER_IMAGE, it)
                } ?: remove(AuthStore.KEY_CURRENT_USER_IMAGE)
            } ?: run {
                remove(AuthStore.KEY_BEARER_TOKEN)
                remove(AuthStore.KEY_BEARER_EXPIRY)
                remove(AuthStore.KEY_REFRESH_TOKEN)
                remove(AuthStore.KEY_REFRESH_EXPIRY)
                remove(AuthStore.KEY_CURRENT_USER_ID)
                remove(AuthStore.KEY_CURRENT_USER_EMAIL)
                remove(AuthStore.KEY_CURRENT_USER_DISPLAYNAME)
                remove(AuthStore.KEY_CURRENT_USER_IMAGE)
            }
            apply()
        }
    }

    private fun fetchUserInfo(
        userId: String
    ) = prefs.getString(AuthStore.KEY_CURRENT_USER_EMAIL, null)?.let { e ->
        prefs.getString(AuthStore.KEY_CURRENT_USER_DISPLAYNAME, null)?.let { n ->
            UserInfo(
                userId, e, n,
                prefs.getString(AuthStore.KEY_CURRENT_USER_IMAGE, null)
            )
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
                            fetchUserInfo(uId)
                        )
                    }
                }
            }
        }
    }
}