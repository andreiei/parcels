import io.kotest.core.spec.style.ShouldSpec
import utils.shouldMatchSnapshot
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStreamReader
import java.io.PrintStream

class ApplicationTest : ShouldSpec({
    should("handle empty input") {
        val inputString = "exit"

        val inputStream = ByteArrayInputStream(inputString.toByteArray())
        val reader = BufferedReader(InputStreamReader(inputStream))

        val outContent = ByteArrayOutputStream()
        val errContent = ByteArrayOutputStream()

        Application(
            input = reader,
            output = PrintStream(outContent),
            error = PrintStream(errContent),
        )
            .invoke()

        mapOf(
            "output" to outContent.toString().trim(),
            "error" to errContent.toString().trim(),
        )
            .shouldMatchSnapshot(this)
    }

    should("handle valid input") {
        val inputString = """
            fredrik@hotmail.com
            fredrik@hotmail.com ignorespace@hotmail.com
            malin@gmail.com

            test@domain..com
            exit
        """.trimIndent()

        val inputStream = ByteArrayInputStream(inputString.toByteArray())
        val reader = BufferedReader(InputStreamReader(inputStream))

        val outContent = ByteArrayOutputStream()
        val errContent = ByteArrayOutputStream()

        Application(
            input = reader,
            output = PrintStream(outContent),
            error = PrintStream(errContent),
        )
            .invoke()

        mapOf(
            "output" to outContent.toString().trim(),
            "error" to errContent.toString().trim(),
        )
            .shouldMatchSnapshot(this)
    }
})
