package utils

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import decodeFromString
import failure.ApiException
import kotlinx.serialization.Serializable

@Serializable
data class ApiRequest(
    val body: String?,
    val path: String,
    val httpMethod: String,
) {
    inline fun <reified T> getBody(): T {
        val rawBody: String = body ?: throw ApiException.BadRequestException("Request body is missing")
        return runCatching {
            decodeFromString<T>(rawBody)
        }
            .getOrElse {
                throw ApiException.BadRequestException("Failed to parse request body into ${T::class.simpleName}")
            }
    }

    fun getCustomPath(): String = "$httpMethod $path"

    companion object {
        fun APIGatewayProxyRequestEvent.toApiRequest(): ApiRequest = ApiRequest(
            body = body,
            path = path,
            httpMethod = httpMethod,
        )
    }
}
