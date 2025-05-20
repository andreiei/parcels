import entity.ParcelId
import entity.PostCode
import kotlinx.serialization.Serializable
import router.parcels.request.ParcelStatus

@Serializable
data class GetParcelsResponse(
    val parcels: List<ParcelResponse>,
)

@Serializable
data class ParcelResponse(
    val id: ParcelId,
    val status: ParcelStatus,
    val weight: Double,
    val postCode: PostCode,
    val address: String,
)
