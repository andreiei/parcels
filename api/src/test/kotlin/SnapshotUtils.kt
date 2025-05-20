import com.karumi.kotlinsnapshot.KotlinSnapshot
import io.kotest.core.descriptors.DescriptorId
import io.kotest.core.test.TestScope
import java.nio.file.Paths

inline fun <reified T> T.shouldMatchSnapshot(
    context: TestScope,
    suffixName: String? = null,
) {
    val qualifiedName: String? = context.testCase.descriptor.spec().kclass.qualifiedName
    val serialized: String = encodeToString(this)
    KotlinSnapshot(
        testClassAsDirectory = true,
        snapshotsFolder = pathForTest(qualifiedName),
    ).matchWithSnapshot(serialized, context.getTestName() + (suffixName?.let { "-$suffixName" } ?: ""))
}

inline infix fun <reified T> T.shouldMatchSnapshot(context: TestScope) {
    shouldMatchSnapshot(context, null)
}

fun pathForTest(qualifiedName: String?): String {
    val moduleName: String = runCatching { System.getProperty("module") }.getOrNull() ?: "test"
    val dir = "/src/$moduleName/kotlin"
    val snapshotsFolder: String? = qualifiedName?.replace(".", "/")
    return Paths.get(dir, snapshotsFolder).parent.toString()
}

fun TestScope.getTestName(): String =
    let {
        val test = it.testCase
        val names = test.descriptor.ids()
        val spec = test.descriptor.spec()
        val specName = spec.kclass.simpleName
        val ids = names.map(DescriptorId::value).slice(1 until names.size)
        "$specName.${ids.joinToString(".")}"
    }
