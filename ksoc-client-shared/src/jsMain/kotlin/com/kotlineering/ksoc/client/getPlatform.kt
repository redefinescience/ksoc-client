package com.kotlineering.ksoc.client

actual fun getPlatform(): Platform = object : Platform {
    override val name: String = "JS Platform"
}