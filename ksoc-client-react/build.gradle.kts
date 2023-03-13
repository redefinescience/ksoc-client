plugins {
    kotlin("js")
}

group = "com.kotlineering"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":ksoc-client-shared"))

    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react:18.2.0-pre.346")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom:18.2.0-pre.346")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-emotion:11.9.3-pre.346")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-router-dom:6.3.0-pre.346")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-redux:4.1.2-pre.346")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-redux:7.2.6-pre.346")
}

kotlin {
    js(IR) {
        binaries.executable()
        browser {
            commonWebpackConfig {
                cssSupport {
                    enabled.set(true)
                }
            }
        }
    }
}
