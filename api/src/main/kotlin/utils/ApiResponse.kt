package utils

import encodeToString
import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse(
    val body: String?,
    val code: Int,
) {
    companion object {
        inline fun <reified T> withContent(body: T, code: Int = 200): ApiResponse =
            ApiResponse(body = encodeToString(body), code = code)

        fun noContent(): ApiResponse = ApiResponse(body = null, code = 204)
    }
}
