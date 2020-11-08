import org.jetbrains.kotlin.config.KotlinCompilerVersion

group = "com.treefrogapps.kotlin.core"
project.extra["name"] = "core"
version = "1.1.0"

dependencies {
    // Kotlin
    implementation(kotlin("stdlib", KotlinCompilerVersion.VERSION))
}