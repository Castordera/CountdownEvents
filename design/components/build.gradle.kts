plugins {
    alias(libs.plugins.castorena.bl.compose.lib)
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