package com.kotlineering.ksoc.client.auth

import kotlinx.datetime.Instant

data class Tokens(
    val bearer: String,
    val bearerExpiry: Instant,
    val refresh: String,
    val refreshExpiry: Instant
)
