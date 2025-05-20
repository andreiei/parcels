package router.parcels.request

import entity.ParcelId
import entity.PostCode
import kotlinx.serialization.Serializable

@Serializable
data class CreateParcelRequest(
    val id: ParcelId,
    val weight: Double,
    val postCode: PostCode,
    val address: String,
    val status: ParcelStatus,
) {
    fun validate() {
        require(weight in 0.1..1000.0) {
            "Weight must be between 0.1 and 1000.0 kg"
        }

        require(postCode.toString().matches(Regex("\\d{4}"))) {
            "Post code must be a 4-digit number"
        }

        require(address.length in 1..255) {
            "Address must be between 1 and 255 characters"
        }
    }
}

enum class ParcelStatus {
    NOT_STARTED,
    IN_TRANSIT,
    DELIVERED,
}
