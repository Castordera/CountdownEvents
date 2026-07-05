plugins {
    alias(libs.plugins.castorena.bl.lib.compose)
    alias(libs.plugins.castorena.bl.kotlin)
    alias(libs.plugins.castorena.bl.common)
    alias(libs.plugins.castorena.bl.test.android)
    alias(libs.plugins.castorena.bl.test.unit)
}

android {
    namespace = "com.ulises.components"
}

dependencies {
    //  Projects
    implementation(project(":design:theme"))
    implementation(project(":common:resources"))
}