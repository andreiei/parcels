package utils

import com.karumi.kotlinsnapshot.KotlinSnapshot
import io.kotest.core.test.TestScope
import java.nio.file.Paths

inline fun <reified T> T.shouldMatchSnapshot(context: TestScope) {
    val testName: String = context.getQualifiedTestName()
    val testClassPath: String? = context.testCase.descriptor.spec().kclass.qualifiedName
    val snapshotPath: String = testClassPath.toSnapshotPath()
    KotlinSnapshot(
        testClassAsDirectory = true,
        snapshotsFolder = snapshotPath,
    ).matchWithSnapshot(encodeToString(this), testName)
}

fun String?.toSnapshotPath(): String {
    val moduleName: String = System.getProperty("module") ?: "test"
    val baseDir = "/src/$moduleName/kotlin"
    return Paths.get(baseDir, this?.replace(".", "/")).parent.toString()
}

fun TestScope.getQualifiedTestName(): String {
    val descriptor = testCase.descriptor
    val className = descriptor.spec().kclass.simpleName ?: "UnknownSpec"
    val testPath = descriptor.ids().drop(1).joinToString(".") { it.value }
    return "$className.$testPath"
}
