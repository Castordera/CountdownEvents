import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import com.ulises.convention.config.configureAndroidTest
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.findByType

class AndroidTestConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            extensions.findByType<ApplicationExtension>()?.apply {
                configureAndroidTest(this)
            }
            extensions.findByType<LibraryExtension>()?.apply {
                configureAndroidTest(this)
            }
        }
    }
}