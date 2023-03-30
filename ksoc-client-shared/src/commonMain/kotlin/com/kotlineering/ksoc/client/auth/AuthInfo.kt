package com.kotlineering.ksoc.client.auth

import kotlinx.datetime.Instant

@kotlinx.serialization.Serializable
data class AuthInfo(
    val userId: String,
    val bearer: String,
    val bearerExpiry: Instant,
    val refresh: String,
    val refreshExpiry: Instant,
)
