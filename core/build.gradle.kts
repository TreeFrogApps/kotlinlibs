import org.jetbrains.kotlin.config.KotlinCompilerVersion

group = "com.treefrogapps.core"
project.extra["name"] = "core"
version = "1.0.0"

dependencies {
    // Kotlin
    implementation(kotlin("stdlib", KotlinCompilerVersion.VERSION))

    // Logging
    implementation("org.apache.logging.log4j:log4j-core:2.8.2")
}