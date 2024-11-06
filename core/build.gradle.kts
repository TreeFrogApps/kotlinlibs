group = "com.treefrogapps.kotlin.core"
project.extra["name"] = "core"
version = "1.20.1"

dependencies {
    // Kotlin
    implementation(libs.kotlin.stdlib)

    testImplementation(libs.junit)
}