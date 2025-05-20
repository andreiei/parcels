import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import failure.FailureHandler
import router.Router
import utils.ApiContext
import utils.ApiRequest.Companion.toApiRequest
import utils.ApiResponse

@Suppress("Unused")
class Handler(
    private val apiContext: ApiContext = ApiContext(),
    private val router: Router = Router(apiContext),
    private val failureHandler: FailureHandler = FailureHandler(),
) : RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    override fun handleRequest(
        event: APIGatewayProxyRequestEvent,
        context: Context?,
    ): APIGatewayProxyResponseEvent {
        val response: ApiResponse = try {
            router(event.toApiRequest())
        } catch (throwable: Throwable) {
            failureHandler(throwable)
        }
        return when (response.body) {
            null -> respond()
            else -> respondWithContent(response.body, response.code)
        }
    }

    private fun respondWithContent(
        body: String,
        statusCode: Int = 200,
    ): APIGatewayProxyResponseEvent = APIGatewayProxyResponseEvent()
        .withStatusCode(statusCode)
        .withBody(body)

    private fun respond(): APIGatewayProxyResponseEvent =
        APIGatewayProxyResponseEvent().withStatusCode(204)
}
