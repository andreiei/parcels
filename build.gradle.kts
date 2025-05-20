import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlin.jvm) apply true
    alias(libs.plugins.spotless) apply true
    alias(libs.plugins.cutterslade.analyze) apply true
    alias(libs.plugins.kotlin.serialization) apply true
}

repositories {
    mavenCentral()
}

dependencies {
    permitUnusedDeclared(libs.kotlin.stdlib)
    permitUnusedDeclared(libs.kotlin.stdlib.jdk8)
}

allprojects {
    apply(plugin = "com.diffplug.spotless")
    apply(plugin = "ca.cutterslade.analyze")
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.serialization")

    repositories {
        mavenCentral()
    }

    kotlin {
        jvmToolchain(17)
    }

    tasks.withType<KotlinCompile>().all {
        kotlinOptions {
            jvmTarget = "17"
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

    tasks.register("runSpotlessApply", org.gradle.api.DefaultTask::class.java) {
        dependsOn("spotlessApply")
        doLast {
            exec {
                workingDir(project.rootDir)
                commandLine("./gradlew", "spotlessApply")
            }
        }
    }
    tasks.named("build") {
        dependsOn("runSpotlessApply")
    }

    spotless {
        kotlin {
            ktlint(libs.versions.ktlint.get())
        }
        kotlinGradle {
            ktlint(libs.versions.ktlint.get())
        }
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "17"
    }
}
