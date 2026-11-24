import org.gradle.api.tasks.testing.Test

plugins {
    id("com.google.gms.google-services")   // debe ir DESPUÉS de registrar el json
    id("com.google.devtools.ksp")
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)

}

android {
    namespace = "com.example.modoguardian"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.example.modoguardian"
        minSdk = 25
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        manifestPlaceholders["WEB_CLIENT_ID"] = "81920368364-4vcks7vt1lql6099jdf88v7n09udd1vo.apps.googleusercontent.com"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
        debug {
            buildConfigField("String", "WEB_CLIENT_ID", "\"81920368364-4vcks7vt1lql6099jdf88v7n09udd1vo.apps.googleusercontent.com\"")
        }
        release {
            buildConfigField("String", "WEB_CLIENT_ID", "\"81920368364-4vcks7vt1lql6099jdf88v7n09udd1vo.apps.googleusercontent.com\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation("androidx.room:room-runtime:2.8.5")
    implementation("androidx.room:room-ktx:2.8.5")
    implementation(libs.googleid)
    ksp("androidx.room:room-compiler:2.8.5")
    implementation("androidx.compose.material:material-icons-extended:1.7.8")
    implementation("io.coil-kt.coil3:coil-compose:3.6.3")
    implementation("io.coil-kt.coil3:coil-network-okhttp:3.6.3")
    implementation("androidx.navigation:navigation-compose:2.10.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.11.0")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.11.0")
    implementation("com.google.android.libraries.sdkcoroutines:sdkcoroutines:1.0.0")
    implementation("androidx.datastore:datastore-preferences:1.2.1")
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material3.window.size.class1)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.kotest.runner.junit5)
    testImplementation(libs.kotest.assertions.core)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
    testRuntimeOnly(libs.junit.platform.launcher)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.junit)
    androidTestImplementation("androidx.navigation:navigation-testing:2.10.2")
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)

    implementation(platform("com.google.firebase:firebase-bom:34.0.0"))
    implementation("com.google.firebase:firebase-auth")

    implementation("androidx.credentials:credentials:1.5.0")
    implementation("androidx.credentials:credentials-play-services-auth:1.5.0")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.10.1")
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
        showStandardStreams = false
    }
}