package com.domains.models

import com.domains.shouldMatchSnapshot
import io.kotest.core.spec.style.FreeSpec

class EmailTest : FreeSpec({
    "should validate domain" {
        listOf(
            "peter@domain.com",
            "peter@sub-domain.domain.com",
            "@domain.com",
            "peter",
            "peter@",
            "peter@domain..com",
            "peter@-domain.com",
        )
            .associateWith { domain ->
                when (Email.from(domain)?.value) {
                    null -> "Invalid"
                    else -> "valid"
                }
            }
            .shouldMatchSnapshot(this)
    }
})
