group = "com.domains"

plugins {
    application
}

dependencies {
    implementation(libs.kotlin.stdlib)
    implementation(libs.annotations)
    implementation(libs.serialization.kotlinx.core.jvm)
    implementation(libs.serialization.kotlinx.json)

    testImplementation(libs.test.kotest.common)
    permitTestUnusedDeclared(libs.test.kotest.common)
    // testImplementation(libs.test.kotest.assertions.shared)
    testImplementation(libs.test.kotest.framework.api)
    testImplementation(libs.test.kotest.runner)
    permitTestUnusedDeclared(libs.test.kotest.runner)
    testImplementation(libs.test.snapshot)
}

application {
    mainClass.set("${project.group}.MainKt")
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}

tasks.withType<Jar> {
    manifest {
        attributes("Main-Class" to "${project.group}.MainKt")
    }
    isZip64 = true
    from(configurations.runtimeClasspath.map { config -> config.map { if (it.isDirectory) it else zipTree(it) } })
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}
