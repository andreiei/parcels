package router.parcels.usecases

import decodeFromString
import entity.ParcelId
import entity.PostCode
import entity.RouteId
import entity.StopId
import io.kotest.core.spec.style.ShouldSpec
import io.mockk.mockk
import router.parcels.request.ParcelStatus
import router.parcels.response.GetRoutesResponse
import router.parcels.usecases.GetRoutesUseCase.Companion.distributeToRoutes
import services.ParcelRepository.Companion.ParcelStorageObject
import shouldMatchSnapshot
import utils.ApiContext

class GetParcelRoutesUseCaseTest : ShouldSpec() {
    init {
        context("distributeToRoutes") {
            val routes: List<RouteId> = listOf(RouteId("R001"), RouteId("R002"), RouteId("R003"))

            should("be handle empty parcels") {
                emptyList<ParcelStorageObject>().distributeToRoutes(routes)
            }
            should("be able to distribute parcels of varying sizes") {
                (0..9)
                    .associateWith { index ->
                        (0..index).map { createParcel(it) }
                            .distributeToRoutes(routes)
                    }
                    .shouldMatchSnapshot(this)
            }
        }
        context(GetRoutesUseCase::invoke.name) {
            val apiContext = ApiContext()
            (0..10)
                .forEach { index ->
                    when (index) {
                        0 -> createParcel(index, "2nd Street")
                        else -> createParcel(index)
                    }
                        .also { apiContext.parcelRepository.addParcel(it) }
                }

            val useCase = GetRoutesUseCase(apiContext)
            val response: GetRoutesResponse = decodeFromString(useCase(mockk()).body!!)
            response
                .routes
                .map { route ->
                    route.copy(stops = route.stops.map { it.copy(id = StopId("REDACTED")) })
                }
                .shouldMatchSnapshot(this)
        }
    }

    private fun createParcel(id: Int, address: String = "123 Main St"): ParcelStorageObject {
        return ParcelStorageObject(
            id = ParcelId("P0$id"),
            weight = 2.3,
            postCode = PostCode("0465"),
            address = address,
            status = ParcelStatus.IN_TRANSIT,
        )
    }
}
