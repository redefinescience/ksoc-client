package com.kotlineering.ksoc.client

actual fun getPlatform(): Platform = object : Platform {
    override val name: String =
        "${kotlin.native.Platform.osFamily}(${kotlin.native.Platform.cpuArchitecture})"
}
