package com.kotlineering.ksoc.client.auth

import kotlinx.datetime.Instant

data class AuthInfo(
    val bearer: String,
    val bearerExpiry: Instant,
    val refresh: String,
    val refreshExpiry: Instant,
    val userId: String
)
