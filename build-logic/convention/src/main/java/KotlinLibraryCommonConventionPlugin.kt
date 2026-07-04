import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

@Suppress("unused")
class KotlinLibraryCommonConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("java-library")
                apply("org.jetbrains.kotlin.jvm")
            }
            configureKotlinJvmToolchain()
        }
    }
}

private fun Project.configureKotlinJvmToolchain() {
    extensions.configure<KotlinJvmProjectExtension> {
        jvmToolchain(21)
    }
}