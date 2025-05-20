package router.parcels

import failure.ApiException
import router.Route
import router.Route.Companion.BasePath
import router.parcels.usecases.CreateParcelUseCase
import router.parcels.usecases.GetParcelsUseCase
import router.parcels.usecases.GetRoutesUseCase
import utils.ApiContext
import utils.ApiRequest
import utils.ApiResponse

class ParcelRoutes(
    private val apiContext: ApiContext,
    private val createParcelUseCase: CreateParcelUseCase = CreateParcelUseCase(apiContext),
    private val getRoutesUseCase: GetRoutesUseCase = GetRoutesUseCase(apiContext),
    private val getParcelsUseCase: GetParcelsUseCase = GetParcelsUseCase(apiContext),
) : Route {
    override val basePath: BasePath = BasePath("/v1/parcels")

    override fun invoke(path: String, request: ApiRequest): ApiResponse {
        return when (path) {
            "POST $basePath" -> createParcelUseCase
            "GET $basePath/today" -> getParcelsUseCase
            "GET $basePath/today/routes" -> getRoutesUseCase
            else -> throw ApiException.NotFoundException("Unsupported method and path: $path")
        }
            .invoke(request)
    }
}
