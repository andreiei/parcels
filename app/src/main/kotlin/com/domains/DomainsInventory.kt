package com.domains

import com.domains.models.Domain

/**
 * DomainsInventory is a collection that tracks the occurrence of domains.
 */
class DomainsInventory {
    private val domains: MutableMap<Domain, Int> = mutableMapOf()

    /**
     * Adds a domain to the collection, incrementing its count.
     */
    fun add(domain: Domain) {
        domains[domain] = domains.getOrDefault(domain, 0) + 1
    }

    /**
     * Returns a list of the top N domains sorted by their count in descending order.
     * @param n The number of top domains to return. Defaults to 10.
     */
    fun get(n: Int = 10): List<DomainsCount> {
        return domains.entries
            .sortedByDescending { it.value }
            .take(n)
            .map { DomainsCount(it.key, it.value) }
    }

    companion object {
        data class DomainsCount(
            val domain: Domain,
            val count: Int,
        )
    }
}
