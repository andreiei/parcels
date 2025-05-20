package router

import kotlinx.serialization.Serializable
import utils.ApiRequest
import utils.ApiResponse

interface Route {
    val basePath: BasePath

    operator fun invoke(path: String, request: ApiRequest): ApiResponse

    companion object {
        @JvmInline
        @Serializable
        value class BasePath(private val value: String) {
            override fun toString(): String = value
        }
    }
}
