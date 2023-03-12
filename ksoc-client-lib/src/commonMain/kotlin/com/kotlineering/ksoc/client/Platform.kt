package com.kotlineering.ksoc.client

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform