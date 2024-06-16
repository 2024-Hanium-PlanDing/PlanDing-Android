plugins {
    id("java-library")
    alias(libs.plugins.org.jetbrains.kotlin.jvm)
    kotlin("kapt")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(libs.coroutines.core)
    implementation("javax.inject:javax.inject:1")
}