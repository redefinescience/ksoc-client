pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "ksoc-client"
include(":ksoc-client-android")
include(":ksoc-client-shared")
include(":ksoc-client-nativetest")
include(":ksoc-client-react")
