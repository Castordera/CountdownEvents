plugins {
    alias(libs.plugins.castorena.bl.kotlin.lib)
    alias(libs.plugins.castorena.bl.test.unit)
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))

    implementation(libs.javax.inject)
    implementation(libs.coroutines.core)
}

tasks.withType<Test> {
    useJUnitPlatform()
}