package router.parcels.usecases

import failure.ApiException
import router.UseCase
import router.parcels.request.CreateParcelRequest
import services.ParcelRepository.Companion.ParcelStorageObject
import utils.ApiContext
import utils.ApiRequest
import utils.ApiResponse

class CreateParcelUseCase(
    private val apiContext: ApiContext,
) : UseCase {
    override operator fun invoke(request: ApiRequest): ApiResponse {
        val requestBody: CreateParcelRequest = request.getBody()
        requestBody.validate()

        return apiContext.parcelRepository.addParcel(
            parcel = ParcelStorageObject(
                id = requestBody.id,
                weight = requestBody.weight,
                postCode = requestBody.postCode,
                address = requestBody.address,
                status = requestBody.status,
            ),
        )
            .fold(
                onSuccess = { ApiResponse.noContent() },
                onFailure = {
                    throw ApiException.FailedToAddParcel(requestBody.id)
                },
            )
    }
}
