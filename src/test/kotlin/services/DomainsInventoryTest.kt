package services

import io.kotest.core.spec.style.ShouldSpec
import models.Domain
import utils.shouldMatchSnapshot

class DomainsInventoryTest : ShouldSpec() {
    init {
        should("get N amount of domains") {
            val domainsInventory = DomainsInventory()

            listOf(
                createDomains("gmail.com", 5),
                createDomains("yahoo.com", 12),
                createDomains("hotmail.com", 8),
            )
                .flatten()
                .forEach {
                    domainsInventory.add(it)
                }

            domainsInventory
                .get()
                .map { "${it.domain}: ${it.count}" }
                .shouldMatchSnapshot(this)
        }
    }

    private fun createDomains(name: String, amount: Int): List<Domain> =
        (1..amount).mapNotNull { Domain.from(name) }
}
