import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import com.ulises.convention.config.configureAndroidAppCommon
import com.ulises.convention.config.configureAndroidCommon
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.findByType

class AndroidCommonConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            extensions.findByType<ApplicationExtension>()?.apply {
                configureAndroidCommon(this)
                configureAndroidAppCommon(this)
            }

            extensions.findByType<LibraryExtension>()?.apply {
                configureAndroidCommon(this)
            }
        }
    }
}