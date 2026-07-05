plugins {
    alias(libs.plugins.castorena.bl.compose.lib)
}

android {
    namespace = "com.ulises.common.resources"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}