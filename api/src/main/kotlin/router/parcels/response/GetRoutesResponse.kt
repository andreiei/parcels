package router.parcels.response

import entity.ParcelId
import entity.RouteId
import entity.StopId
import kotlinx.serialization.Serializable

@Serializable
data class GetRoutesResponse(
    val routes: List<Route>,
)

@Serializable
data class Route(
    val id: RouteId,
    val stops: List<Stop>,
)

@Serializable
data class Stop(
    val id: StopId,
    val address: String,
    val parcels: List<ParcelId>,
)
