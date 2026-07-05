plugins {
    alias(libs.plugins.castorena.bl.kotlin.lib)
    id("kotlinx-serialization")
}

dependencies {
    implementation(libs.kotlinx.serialization.json)
}