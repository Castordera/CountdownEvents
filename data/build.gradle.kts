plugins {
    alias(libs.plugins.castorena.bl.kotlin.lib)
}

dependencies {
    implementation(project(":domain"))

    implementation(libs.javax.inject)
    implementation(libs.coroutines.core)
    //
    testImplementation(libs.bundles.tests.unit)
}

tasks.withType<Test> {
    useJUnitPlatform()
}