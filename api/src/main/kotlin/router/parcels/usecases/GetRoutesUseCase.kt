package router.parcels.usecases

import entity.RouteId
import entity.StopId
import router.UseCase
import router.parcels.response.GetRoutesResponse
import router.parcels.response.Route
import router.parcels.response.Stop
import services.ParcelRepository.Companion.ParcelStorageObject
import services.RoutesService.RouteServiceLocation
import utils.ApiContext
import utils.ApiRequest
import utils.ApiResponse
import java.time.LocalDate
import java.time.ZoneOffset
import java.util.UUID

class GetRoutesUseCase(
    private val apiContext: ApiContext,
) : UseCase {
    override operator fun invoke(request: ApiRequest): ApiResponse {
        val today: LocalDate = LocalDate.now(ZoneOffset.UTC)

        val parcels: List<ParcelStorageObject> = apiContext.parcelRepository.getParcels(today)
        val routeIds: List<RouteId> = apiContext.routesService.getRoutes(RouteServiceLocation.PARCEL_MEADOW)

        val distributedParcels: Map<RouteId, List<ParcelStorageObject>> = parcels.distributeToRoutes(routeIds)

        val routes: List<Route> = distributedParcels
            .map { (routeId, routeParcels) ->
                val stopsByAddress: Map<String, List<ParcelStorageObject>> = routeParcels.groupBy { it.address }
                val stops: List<Stop> = stopsByAddress.map { (address, groupedParcels) ->
                    Stop(
                        id = StopId(UUID.randomUUID().toString()),
                        address = address,
                        parcels = groupedParcels.map(ParcelStorageObject::id),
                    )
                }
                Route(routeId, stops)
            }
        return ApiResponse.withContent(GetRoutesResponse(routes = routes))
    }

    companion object {
        fun List<ParcelStorageObject>.distributeToRoutes(
            routes: List<RouteId>,
        ): Map<RouteId, List<ParcelStorageObject>> {
            val routeIndexMap: MutableMap<Int, MutableList<ParcelStorageObject>> = mutableMapOf()
            forEachIndexed { index, parcel ->
                routeIndexMap.computeIfAbsent(index % routes.size) { mutableListOf() }.add(parcel)
            }
            return routes.mapIndexed { index, routeId ->
                routeId to routeIndexMap[index].orEmpty()
            }
                .toMap()
                .filterValues { it.isNotEmpty() }
        }
    }
}
