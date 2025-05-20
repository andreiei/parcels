package services

import entity.RouteId

/**
 * Mock for a possible service that would fetch routes from a database and return them.
 */
class RoutesService {
    private val routes: Map<RouteServiceLocation, List<RouteId>> = mapOf(
        RouteServiceLocation.PARCEL_MEADOW to listOf(
            RouteId("R001"),
            RouteId("R002"),
            RouteId("R003"),
            RouteId("R004"),
            RouteId("R005"),
        ),
    )

    fun getRoutes(location: RouteServiceLocation): List<RouteId> = routes[location] ?: emptyList()

    enum class RouteServiceLocation {
        PARCEL_MEADOW,
    }
}
