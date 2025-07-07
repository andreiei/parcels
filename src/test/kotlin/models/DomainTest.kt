package models

import io.kotest.core.spec.style.ShouldSpec
import utils.shouldMatchSnapshot

class DomainTest : ShouldSpec({
    should("validate domains") {
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
                when (Domain.from(domain)) {
                    null -> "❌"
                    else -> "✅"
                }
            }
            .shouldMatchSnapshot(this)
    }
})
