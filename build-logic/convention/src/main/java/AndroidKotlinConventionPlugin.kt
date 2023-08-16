import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import com.ulises.convention.config.configureKotlin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.findByType

class AndroidKotlinConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("org.jetbrains.kotlin.android")

            extensions.findByType<ApplicationExtension>()?.apply {
                configureKotlin(this)
            }

            extensions.findByType<LibraryExtension>()?.apply {
                configureKotlin(this)
            }
        }
    }
}