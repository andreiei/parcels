package models

import org.apache.commons.validator.routines.EmailValidator

@JvmInline
value class Email private constructor(val value: String) {
    override fun toString(): String = value

    companion object {
        fun from(value: String): Email? {
            val email = Email(value.trim())
            return if (EmailValidator.getInstance().isValid(value)) email else null
        }
    }
}
