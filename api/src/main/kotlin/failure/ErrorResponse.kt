package failure

import io.netty.handler.codec.http.HttpResponseStatus
import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val error: ErrorCode,
    val description: String,
    val code: Int,
) {
    constructor(error: ErrorCode, description: String?, status: HttpResponseStatus) : this(
        error = error,
        description = description ?: status.reasonPhrase(),
        code = status.code(),
    )
}
