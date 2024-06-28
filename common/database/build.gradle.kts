plugins {
    id("countdown.android.library.compose")
    id("countdown.android.kotlin")
    id("countdown.android.common")
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.ulises.common.database"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    //
    implementation(project(":domain"))
    implementation(project(":data"))
    //  Room
    implementation(libs.bundles.room.core)
    ksp(libs.room.coompiler)
    //  Others
    implementation(libs.javax.inject)
    //
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}