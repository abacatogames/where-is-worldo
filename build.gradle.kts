plugins {
    kotlin("jvm") version "2.2.0"
    kotlin("plugin.serialization") version "2.2.0"
    id("io.ktor.plugin") version "3.4.1"
    application
}

group = "com.abacatogames"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-serialization-kotlinx-json:3.4.1")
    implementation("io.ktor:ktor-server-content-negotiation:3.4.1")
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlinx:kotlinx-html:0.12.0")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-css:2025.11.5")
    implementation("io.ktor:ktor-server-core")
    implementation("io.ktor:ktor-server-netty")
    implementation("io.ktor:ktor-server-html-builder")
    implementation("io.ktor:ktor-server-sessions")
    implementation("io.ktor:ktor-server-caching-headers")
    implementation("net.sf.geographiclib:GeographicLib-Java:2.1")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}

application {
    mainClass = "com.abacatogames.WebAppKt"
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}
