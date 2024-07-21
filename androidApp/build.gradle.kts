plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    id("kotlin-kapt")
}

android {
    namespace = "com.kft.learnkmp.android"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.kft.learnkmp.android"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
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
    implementation(projects.shared)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material3)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.startup.runtime)
    implementation(libs.play.services.location)
    debugImplementation(libs.compose.ui.tooling)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    val nav_version = "2.7.7"

    implementation("androidx.navigation:navigation-compose:$nav_version")
    implementation("com.google.code.gson:gson:2.10")

    implementation("io.insert-koin:koin-androidx-compose:3.5.3")

    implementation("io.insert-koin:koin-android:3.5.3")

    implementation("com.google.accompanist:accompanist-permissions:0.35.1-alpha")
}

kapt {   correctErrorTypes =  true }
