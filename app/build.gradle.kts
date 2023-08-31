plugins {
    id("countdown.android.app.compose")
    id("countdown.android.kotlin")
    id("countdown.android.common")
    id("countdown.android.test")
    id("countdown.test")
    id("com.google.dagger.hilt.android")
    id("kotlin-parcelize")
    id("kotlinx-serialization")
    id("kotlin-kapt")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.countdownapp"

    defaultConfig {
        applicationId = "com.example.countdownapp"
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation(libs.android.lifecycle.runtime.ktx)
    //  Data store
    implementation(libs.data.store)
    //  Compose
    implementation(libs.compose.lifecycle.runtime)
    implementation(libs.compose.activity)
    implementation(libs.compose.material3)
    implementation(libs.compose.navigation)
    //  Room
    implementation(libs.bundles.room.core)
    ksp(libs.room.coompiler)
    //  Hilt
    implementation(libs.bundles.hilt.core)
    kapt(libs.hilt.compiler)
    implementation(libs.kotlinx.serialization.json)

    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":usecase"))
    implementation(project(":design:components"))
    implementation(project(":design:theme"))
    //  Features
    implementation(project(":feature:list"))
    implementation(project(":feature:addEvent"))
    implementation(project(":feature:event-detail"))
}

kapt {
    correctErrorTypes = true
}