plugins {
    id("countdown.android.app.compose")
    id("countdown.android.kotlin")
    id("countdown.android.common")
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
    //  Coil
    implementation(libs.bundles.coil)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.timber)

    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(project(":usecase"))
    implementation(project(":design:components"))
    implementation(project(":design:theme"))
    //  Features
    implementation(project(":feature:list"))

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    val compose_version = "1.4.2"
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$compose_version")
    debugImplementation("androidx.compose.ui:ui-test-manifest:$compose_version")
}

kapt {
    correctErrorTypes = true
}