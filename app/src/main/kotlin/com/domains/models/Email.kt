package com.domains.models

@JvmInline
value class Email private constructor(val value: String) {
    override fun toString(): String = value

    fun isValid(): Boolean {
        val emailRegex =
            Regex("""^[a-zA-Z0-9._%+-]+@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?\.)+[a-zA-Z]{2,}$""")
        return emailRegex.matches(value)
    }

    fun getDomain(): Domain? = Domain.from(value.substringAfter('@'))

    companion object {
        fun from(value: String): Email? {
            val email = Email(value.trim())
            return if (email.isValid()) email else null
        }
    }
}
