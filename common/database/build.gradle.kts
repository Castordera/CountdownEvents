plugins {
    id("countdown.android.library.compose")
    id("countdown.android.kotlin")
    id("countdown.android.common")
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.ulises.common.database"
}

dependencies {
    //
    implementation(project(":domain"))
    implementation(project(":data"))
    //  Room
    implementation(libs.bundles.room.core)
    ksp(libs.room.coompiler)
    implementation(libs.kotlinx.serialization.json)
    //  Others
    implementation(libs.javax.inject)
    //
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
}