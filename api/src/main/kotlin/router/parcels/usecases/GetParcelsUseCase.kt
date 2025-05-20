package router.parcels.usecases

import GetParcelsResponse
import ParcelResponse
import router.UseCase
import services.ParcelRepository.Companion.ParcelStorageObject
import utils.ApiContext
import utils.ApiRequest
import utils.ApiResponse
import java.time.LocalDate
import java.time.ZoneOffset

class GetParcelsUseCase(
    private val apiContext: ApiContext,
) : UseCase {
    override operator fun invoke(request: ApiRequest): ApiResponse {
        val today: LocalDate = LocalDate.now(ZoneOffset.UTC)
        val parcels: List<ParcelStorageObject> = apiContext.parcelRepository.getParcels(today)

        val response = GetParcelsResponse(
            parcels = parcels.map { parcel ->
                ParcelResponse(
                    id = parcel.id,
                    weight = parcel.weight,
                    postCode = parcel.postCode,
                    address = parcel.address,
                    status = parcel.status,
                )
            },
        )
        return ApiResponse.withContent(response)
    }
}
