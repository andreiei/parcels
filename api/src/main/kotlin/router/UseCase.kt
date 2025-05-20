package router

import utils.ApiRequest
import utils.ApiResponse

interface UseCase {
    operator fun invoke(request: ApiRequest): ApiResponse
}
