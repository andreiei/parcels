dependencies {
    implementation(libs.kotlin.stdlib)
    implementation(libs.annotations)
    implementation(libs.serialization.kotlinx.core.jvm)
    implementation(libs.serialization.kotlinx.json)
    implementation(libs.netty)
    implementation(libs.aws.lambda.java.core)
    implementation(libs.aws.lambda.java.events)

    testImplementation(libs.test.kotest.common)
    permitTestUnusedDeclared(libs.test.kotest.common)
    testImplementation(libs.mockk.jvm)
    testImplementation(libs.mockk.dsl.jvm)
    testImplementation(libs.test.kotest.assertions.shared)
    testImplementation(libs.test.kotest.framework.api)
    testImplementation(libs.test.kotest.runner)
    permitTestUnusedDeclared(libs.test.kotest.runner)
    testImplementation(libs.test.snapshot)
}
