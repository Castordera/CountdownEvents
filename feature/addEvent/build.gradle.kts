plugins {
    id("countdown.android.library.compose")
    id("countdown.android.kotlin")
    id("countdown.android.common")
    id("countdown.android.test")
    id("countdown.test")
    id("com.google.devtools.ksp")
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
    implementation(project(":common:preview-data"))
    implementation(project(":design:theme"))
    implementation(project(":design:components"))
    implementation(project(":common:navigation"))
    implementation(project(":common:resources"))
    //  Hilt
    implementation(libs.bundles.hilt.core)
    ksp(libs.hilt.compiler)
    // Compose
    implementation(libs.compose.lifecycle.runtime)
    implementation(libs.compose.navigation)
    //  Tests
    testImplementation(libs.bundles.tests.unit)
    androidTestImplementation(libs.bundles.tests.compose)
}

tasks.withType<Test> {
    useJUnitPlatform()
}