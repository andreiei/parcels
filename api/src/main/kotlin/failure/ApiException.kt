package failure

import entity.ParcelId

object ApiException {
    class BadRequestException(message: String = "Bad request") : Exception(message)
    class UnauthorizedException(message: String) : Exception(message)
    class NotFoundException(message: String) : Exception(message)
    class FailedToAddParcel(parcelId: ParcelId) : Exception("Failed to add parcel for ID: $parcelId")
}
