package com.kotlineering.ksoc.client.auth

data class Tokens(
    val bearer: String,
    val refresh: String
)
