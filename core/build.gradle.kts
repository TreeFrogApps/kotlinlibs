import org.jetbrains.kotlin.config.KotlinCompilerVersion

group = "com.treefrogapps.kotlin.core"
project.extra["name"] = "core"
version = "1.20.0"

dependencies {
    // Kotlin
    implementation(libs.kotlin.stdlib)

    testImplementation(libs.junit)
}