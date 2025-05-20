package entity

import kotlinx.serialization.Serializable

@JvmInline
@Serializable
value class RouteId(private val value: String) {
    override fun toString(): String = value
}
