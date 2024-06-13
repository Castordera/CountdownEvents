plugins {
    id("countdown.android.app.compose")
    id("countdown.android.kotlin")
    id("countdown.android.common")
    id("countdown.android.test")
    id("countdown.test")
    id("com.google.gms.google-services")
    id("com.google.dagger.hilt.android")
    id("kotlin-parcelize")
    id("kotlinx-serialization")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.countdownapp"

    defaultConfig {
        applicationId = "com.example.countdownapp"
        versionCode = 3
        versionName = "1.2"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(platform(libs.firebase.bom))
    implementation(libs.android.lifecycle.runtime.ktx)
    //  Data store
    implementation(libs.data.store)
    //  Compose
    implementation(libs.compose.lifecycle.runtime)
    implementation(libs.compose.activity)
    implementation(libs.compose.navigation)
    //  Room
    implementation(libs.bundles.room.core)
    ksp(libs.room.coompiler)
    //  Hilt
    implementation(libs.bundles.hilt.core)
    ksp(libs.hilt.compiler)
    implementation(libs.kotlinx.serialization.json)

    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":usecase"))
    implementation(project(":design:components"))
    implementation(project(":design:theme"))
    implementation(project(":common:navigation"))
    implementation(project(":common:datastore"))
    implementation(project(":common:date-utils"))
    //  Features
    implementation(project(":feature:list"))
    implementation(project(":feature:addEvent"))
    implementation(project(":feature:event-detail"))
}