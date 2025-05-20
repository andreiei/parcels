package utils

import services.ParcelRepository
import services.RoutesService

data class ApiContext(
    val parcelRepository: ParcelRepository = ParcelRepository(),
    val routesService: RoutesService = RoutesService(),
)
