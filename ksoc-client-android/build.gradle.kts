plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "com.kotlineering.ksoc.client.android"
    compileSdk = 33
    defaultConfig {
        applicationId = "com.kotlineering.ksoc.client.android"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.0"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "/META-INF/versions/9/**" // Coroutines 1.7-Beta problem?
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":ksoc-client-shared"))

    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")

    implementation("androidx.compose.ui:ui:1.3.1")
    implementation("androidx.compose.ui:ui-tooling:1.3.1")
    implementation("androidx.compose.ui:ui-tooling-preview:1.3.1")
    implementation("androidx.compose.foundation:foundation:1.3.1")
    implementation("androidx.compose.material:material:1.3.1")

    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.1")

    implementation("androidx.activity:activity-compose:1.6.1")

    implementation("androidx.navigation:navigation-compose:2.5.3")

    implementation("io.insert-koin:koin-core:3.3.2")
    implementation("io.insert-koin:koin-android:3.3.2")
    implementation("io.insert-koin:koin-androidx-compose:3.4.1")
}
