plugins {
    alias(libs.plugins.castorena.bl.kotlin.lib)
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    implementation(libs.kotlinx.serialization.json)
}