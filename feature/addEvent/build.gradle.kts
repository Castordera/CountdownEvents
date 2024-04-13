plugins {
    id("countdown.android.library.compose")
    id("countdown.android.kotlin")
    id("countdown.android.common")
    id("countdown.android.test")
    id("countdown.test")
    id("kotlin-kapt")
}

android {
    namespace = "com.ulises.addevent"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":usecase"))
    implementation(project(":common:date-utils"))
    implementation(project(":design:theme"))
    implementation(project(":design:components"))
    implementation(project(":common:navigation"))
    //  Hilt
    implementation(libs.bundles.hilt.core)
    kapt(libs.hilt.compiler)
    // Compose
    implementation(libs.compose.lifecycle.runtime)
}

kapt {
    correctErrorTypes = true
}