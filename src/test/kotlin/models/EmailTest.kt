package models

import io.kotest.core.spec.style.ShouldSpec
import utils.shouldMatchSnapshot

class EmailTest : ShouldSpec({
    should("validate domains") {
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
                when (Email.from(domain)) {
                    null -> "❌"
                    else -> "✅"
                }
            }
            .shouldMatchSnapshot(this)
    }
})
