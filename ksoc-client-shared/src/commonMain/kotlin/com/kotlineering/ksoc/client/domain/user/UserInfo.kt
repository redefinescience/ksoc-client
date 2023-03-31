package com.kotlineering.ksoc.client.domain.user

@kotlinx.serialization.Serializable
data class UserInfo(
    val id: String,
    val email: String,
    val displayName: String,
    val image: String?
)
