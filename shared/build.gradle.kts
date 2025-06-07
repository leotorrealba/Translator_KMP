import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.apollo.graphql)
    alias(libs.plugins.sqldelight)
}

kotlin {
    androidTarget {
        compilations.all {
            compileTaskProvider.configure {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_19)
                }
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
            isStatic = false
        }
    }

    sourceSets {
        commonMain.dependencies {
            // Coroutines
            implementation(libs.kotlin.coroutines.core)

            // Serialization
            implementation(libs.kotlin.serialization.json)

            // DateTime
            implementation(libs.kotlin.datetime)

            // Networking - Ktor
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.logging)

            // GraphQL - Apollo
            implementation(libs.apollo.runtime)
            implementation(libs.apollo.normalized.cache)

            // Database - SQLDelight
            implementation(libs.sqldelight.runtime)
            implementation(libs.sqldelight.coroutines)

            // Dependency Injection - Koin
            implementation(libs.koin.core)
        }

        androidMain.dependencies {
            // Android-specific Ktor client
            implementation(libs.ktor.client.android)

            // Android SQLDelight driver
            implementation(libs.sqldelight.android.driver)
        }

        iosMain.dependencies {
            // iOS-specific Ktor client
            implementation(libs.ktor.client.darwin)

            // iOS SQLDelight driver
            implementation(libs.sqldelight.native.driver)

        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.assertk)
            implementation(libs.turbine)
        }
    }
}

android {
    namespace = "com.leotorrealba.translator_kmp"
    compileSdk = 35
    defaultConfig {
        minSdk = 29
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_19
        targetCompatibility = JavaVersion.VERSION_19
    }
}

sqldelight {
    databases {
        create("TranslateDatabase") {
            packageName.set("com.leotorrealba.translator_kmp.database")
        }
    }
}
