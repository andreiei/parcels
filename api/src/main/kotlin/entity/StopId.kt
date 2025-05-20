package entity

import kotlinx.serialization.Serializable

@JvmInline
@Serializable
value class StopId(private val value: String) {
    override fun toString(): String = value
}
