package com.kotlineering.ksoc.client.domain.auth

import com.kotlineering.ksoc.client.domain.user.UserInfo
import kotlinx.datetime.Instant

@kotlinx.serialization.Serializable
data class AuthInfo(
    val userId: String,
    val bearer: String,
    val bearerExpiry: Instant,
    val refresh: String,
    val refreshExpiry: Instant,
    val userInfo: UserInfo?
)
