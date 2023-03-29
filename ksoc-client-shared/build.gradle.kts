plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")

    id("com.android.library")
}

kotlin {

    // Android
    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    // Native
    val hostOs = System.getProperty("os.name")
    val isMingwX64 = hostOs.startsWith("Windows")
    val nativeTarget = when {
        hostOs == "Mac OS X" -> macosX64("native")
        hostOs == "Linux" -> linuxX64("native")
        isMingwX64 -> mingwX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }

    // iOS
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "ksoc-client-shared"
        }
    }

    // js
    js(IR) {
        browser {
            commonWebpackConfig {
                cssSupport {
                    enabled.set(true)
                }
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.0-Beta")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
                implementation("io.insert-koin:koin-core:3.3.2")
                implementation("io.ktor:ktor-client-core:2.2.4")
                implementation("io.ktor:ktor-serialization-kotlinx-json:2.2.4")
                implementation("io.ktor:ktor-client-content-negotiation:2.2.4")
                implementation("io.ktor:ktor-client-auth:2.2.4")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        val androidMain by getting {
            dependencies {
                implementation("io.insert-koin:koin-android:3.3.2")
                implementation("io.ktor:ktor-client-android:2.2.4")
            }
        }
        val androidUnitTest by getting

        val nativeMain by getting
        val nativeTest by getting

        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                implementation("io.ktor:ktor-client-darwin:2.2.4")
            }
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }

        val jsMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-js:2.2.4")
            }
        }
        val jsTest by getting
    }
}

android {
    namespace = "com.kotlineering.ksoc.client" //.shared?
    compileSdk = 33
    defaultConfig {
        minSdk = 24
        targetSdk = 33
    }
}