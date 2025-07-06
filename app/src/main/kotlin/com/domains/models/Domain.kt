package com.domains.models

@JvmInline
value class Domain private constructor(val value: String) {
    override fun toString(): String = value

    companion object {
        private val domainRegex = Regex("""^(?!-)(?!.*--)(?!.*\.\.)([a-zA-Z0-9-]+\.)+[a-zA-Z]{2,}$""")
        fun from(raw: String): Domain? {
            val cleaned: String = raw.trim().lowercase()
            return if (domainRegex.matches(cleaned)) Domain(cleaned) else null
        }
    }
}
