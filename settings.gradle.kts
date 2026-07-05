pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    includeBuild("../build-logic")
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "CountdownApp"
include(":app")
include(":domain")
include(":usecase")
include(":data")
include(":design:components")
include(":design:theme")
include(":feature:list")
include(":common:preview-data")
include(":feature:addEvent")
include(":feature:event-detail")
include(":common:date-utils")
include(":common:navigation")
include(":common:datastore")
include(":common:resources")
include(":common:database")
include(":common:notification")
