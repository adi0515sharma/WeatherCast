plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)

    kotlin("plugin.serialization") version "1.9.10"
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core.common)
            implementation(libs.lifecycle.viewmodel.compose)

            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.encoding)

            implementation(libs.room.runtime)
            implementation(libs.sqlite.bundled)

            implementation("io.insert-koin:koin-core:3.5.3")
            implementation("io.insert-koin:koin-annotations:1.3.0")
        }

        androidMain.dependencies{
            implementation(libs.kotlinx.coroutines.android)
            implementation(libs.ktor.client.okhttp)
            implementation("androidx.startup:startup-runtime:1.1.1")
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.6.0")
            implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.1")

            implementation("com.google.android.gms:play-services-location:21.3.0")
            implementation(libs.room.runtime.android)

        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }

}

android {
    namespace = "com.kft.learnkmp"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

room {
    schemaDirectory("$projectDir/schemas")
}

dependencies {
    implementation(libs.androidx.core.i18n)
    ksp(libs.room.compiler)
}
ksp {
    arg("KOIN_CONFIG_CHECK","true")
}

