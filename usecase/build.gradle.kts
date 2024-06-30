plugins {
    id("countdown.kotlin.library")
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))

    implementation(libs.javax.inject)
    implementation(libs.coroutines.core)
    //
    testImplementation(libs.bundles.unit.tests)
}

tasks.withType<Test> {
    useJUnitPlatform()
}