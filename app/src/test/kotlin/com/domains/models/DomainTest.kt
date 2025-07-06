package com.domains.models

import com.domains.shouldMatchSnapshot
import io.kotest.core.spec.style.FreeSpec

class DomainTest : FreeSpec({
    "should validate domain" {
        listOf(
            "domain.com",
            "subdomain.domain.com",
            "sub-domain.domain.com",
            "domain",
            "@domain",
            "domain..com",
            "peter@-domain.com",
        )
            .associateWith { domain ->
                when (Domain.from(domain)?.value) {
                    null -> "Invalid"
                    else -> "valid"
                }
            }
            .shouldMatchSnapshot(this)
    }
})
