package failure

import kotlinx.serialization.Serializable

@Serializable
enum class ErrorCode {
    BAD_REQUEST,
    UNAUTHORIZED,
    NOT_FOUND,
    INTERNAL_SERVER_ERROR,
    FAILED_TO_CREATE_PARCEL,
}
