import org.jetbrains.kotlin.config.KotlinCompilerVersion

group = "com.treefrogapps.kotlin.coroutines"
project.extra["name"] = "coroutines"
version = "1.1.1"

dependencies {
    // Kotlin
    implementation(kotlin("stdlib", KotlinCompilerVersion.VERSION))

    // Kotlin Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.1")

    testImplementation("junit:junit:4.13.1")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.4.1")
}