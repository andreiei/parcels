import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlin.jvm) apply true
    alias(libs.plugins.spotless) apply true
    alias(libs.plugins.cutterslade.analyze) apply true
    alias(libs.plugins.kotlin.serialization) apply true
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.kotlin.stdlib)
    permitUnusedDeclared(libs.kotlin.stdlib.jdk8)

    implementation(libs.annotations)
    implementation(libs.serialization.kotlinx.core.jvm)
    implementation(libs.serialization.kotlinx.json)
    implementation(libs.commons.validator)

    testImplementation(libs.test.kotest.common)
    permitTestUnusedDeclared(libs.test.kotest.common)
    testImplementation(libs.test.kotest.framework.api)
    testImplementation(libs.test.kotest.runner)
    permitTestUnusedDeclared(libs.test.kotest.runner)
    testImplementation(libs.test.snapshot)
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}

application {
    mainClass.set("MainKt")
}

tasks.withType<Jar> {
    manifest {
        println(project.group)
        attributes("Main-Class" to "MainKt")
    }
    isZip64 = true
    from(configurations.runtimeClasspath.map { config -> config.map { if (it.isDirectory) it else zipTree(it) } })
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

kotlin {
    jvmToolchain(17)
}

tasks.withType<KotlinCompile>().all {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
    }
}
tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
        showStackTraces = true
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
    }
}

tasks.named("build") {
    dependsOn("spotlessApply")
}

spotless {
    kotlin {
        ktlint(libs.versions.ktlint.get())
    }
    kotlinGradle {
        ktlint(libs.versions.ktlint.get())
    }
}
