import models.Domain
import models.Email
import services.DomainsInventory
import services.DomainsInventory.Companion.DomainsCount
import java.io.BufferedReader
import java.io.PrintStream

class Application(
    private val input: BufferedReader = System.`in`.bufferedReader(),
    private val output: PrintStream = System.out,
    private val error: PrintStream = System.err,
    private val domainsInventory: DomainsInventory = DomainsInventory(),
) {
    operator fun invoke() {
        output.println("Enter one email address per line (type 'exit' to finish)")

        while (true) {
            val line: String = input.readLine().trim().lowercase()
            if (line == "exit") break
            if (line.isBlank() || line.contains(' ')) continue

            when (val email: Email? = Email.from(line)) {
                null -> error.println("Invalid email: '$line' — skipping.")
                else -> Domain.getDomain(email)?.let { domainsInventory.add(it) }
            }
        }

        val topDomains: List<DomainsCount> = domainsInventory.get(10)
        printOutDomains(topDomains)
    }

    private fun printOutDomains(foundDomains: List<DomainsCount>) {
        if (foundDomains.isEmpty()) {
            output.println("No email domains found.")
            return
        }

        output.println("\nTop ${foundDomains.size} domains")
        foundDomains.forEach { domain ->
            output.println("${domain.domain.value} ${domain.count}")
        }
    }
}
