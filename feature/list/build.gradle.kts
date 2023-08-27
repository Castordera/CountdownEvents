plugins {
    id("countdown.android.library.compose")
    id("countdown.android.kotlin")
    id("countdown.android.common")
    id("countdown.android.test")
    id("kotlin-kapt")
}

android {
    namespace = "com.ulises.list"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    //  Projects
    implementation(project(":domain"))
    implementation(project(":usecase"))
    implementation(project(":data"))
    implementation(project(":design:theme"))
    implementation(project(":design:components"))
    //  Hilt
    implementation(libs.bundles.hilt.core)
    kapt(libs.hilt.compiler)
    // Compose
    implementation(libs.compose.material3)
    implementation(libs.compose.lifecycle.runtime)
    //
    implementation(libs.bundles.coil)
    implementation(libs.timber)
    testImplementation(libs.junit)
}

kapt {
    correctErrorTypes = true
}