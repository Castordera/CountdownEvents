plugins {
    alias(libs.plugins.castorena.bl.compose.lib)
    alias(libs.plugins.castorena.bl.test.android)
    alias(libs.plugins.castorena.bl.test.unit)
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.ulises.event_detail"
}

dependencies {
    //  Projects
    implementation(project(":domain"))
    implementation(project(":usecase"))
    //
    implementation(project(":common:date-utils"))
    implementation(project(":design:theme"))
    implementation(project(":design:components"))
    implementation(project(":common:preview-data"))
    implementation(project(":common:navigation"))
    implementation(project(":common:resources"))
    //  Hilt
    implementation(libs.bundles.hilt.core)
    ksp(libs.hilt.compiler)
    ksp(libs.kotlin.metadata.jvm)
    // Compose
    implementation(libs.compose.lifecycle.runtime)
    implementation(libs.compose.navigation)
}