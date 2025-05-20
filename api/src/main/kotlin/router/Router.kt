package router

import failure.ApiException
import router.Route.Companion.BasePath
import router.parcels.ParcelRoutes
import utils.ApiContext
import utils.ApiRequest
import utils.ApiResponse
import kotlin.reflect.KProperty0

class Router(
    private val apiContext: ApiContext,
    private val parcelRoutes: ParcelRoutes = ParcelRoutes(apiContext),
) {
    operator fun invoke(request: ApiRequest): ApiResponse {
        val routesMap: Map<BasePath, KProperty0<Route>> =
            mapOf(
                parcelRoutes.basePath to ::parcelRoutes,
            )

        val customPath: String = request.getCustomPath()
        for ((prefix: BasePath, routeHandler: KProperty0<Route>) in routesMap) {
            if (request.path.startsWith(prefix.toString())) {
                return routeHandler.get().invoke(customPath, request)
            }
        }
        throw ApiException.NotFoundException("Unsupported path: $customPath")
    }
}
