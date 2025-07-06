package com.domains

import com.domains.DomainsInventory.Companion.DomainsCount
import com.domains.models.Email

class Application(
    private val domainsInventory: DomainsInventory = DomainsInventory(),
) {
    fun start() {
        println("Enter one email address per line (type 'e' to finish)")

        while (true) {
            val line: String = readlnOrNull()?.trim() ?: break
            if (line.contains(' ')) {
                System.err.println("Input contains spaces, skipping: '$line'")
                continue
            }
            if (line.equals("e", ignoreCase = true)) break

            when (val email: Email? = Email.from(line)) {
                null -> System.err.println("Invalid email: '$line' — skipping.")
                else -> email.getDomain()?.let { domainsInventory.add(it) }
            }
        }

        val foundDomains: List<DomainsCount> = domainsInventory.get(10)
        printOutDomains(foundDomains)
    }

    private fun printOutDomains(foundDomains: List<DomainsCount>) {
        when (foundDomains.size) {
            0 -> ("No email domains found.").also { return }
            else -> {
                println("\nTop ${foundDomains.size} domains:")
                foundDomains.forEach { domain ->
                    println("${domain.domain.value} ${domain.count}")
                }
            }
        }
    }
}
