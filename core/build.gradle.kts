import org.jetbrains.kotlin.config.KotlinCompilerVersion

group = "com.treefrogapps.kotlin.core"
project.extra["name"] = "core"
version = "1.15.0"

dependencies {
    // Kotlin
    implementation(kotlin("stdlib", KotlinCompilerVersion.VERSION))

    testImplementation("junit:junit:4.13.2")
}