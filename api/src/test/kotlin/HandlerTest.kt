import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import entity.ParcelId
import entity.PostCode
import entity.StopId
import failure.ErrorCode
import failure.ErrorResponse
import io.kotest.assertions.throwables.shouldNotThrow
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.netty.handler.codec.http.HttpMethod
import router.parcels.request.CreateParcelRequest
import router.parcels.request.ParcelStatus
import router.parcels.response.GetRoutesResponse
import utils.ApiContext

class HandlerTest : ShouldSpec({
    val handler = Handler()

    should("call endpoint to create parcel") {
        shouldNotThrow<Exception> {
            handler.handleRequest(
                context = null,
                event = APIGatewayProxyRequestEvent()
                    .withBody(
                        encodeToString(
                            CreateParcelRequest(
                                id = ParcelId("P001"),
                                weight = 2.3,
                                postCode = PostCode("0465"),
                                address = "123 Main St",
                                status = ParcelStatus.IN_TRANSIT,
                            ),
                        ),
                    )
                    .withHttpMethod(HttpMethod.POST.name())
                    .withPath("/v1/parcels"),
            )
        }
    }

    should("call endpoint to fetch today's parcels") {
        val response: APIGatewayProxyResponseEvent = handler.handleRequest(
            context = null,
            event = APIGatewayProxyRequestEvent()
                .withHttpMethod(HttpMethod.GET.name())
                .withPath("/v1/parcels/today"),
        )

        decodeFromString<GetParcelsResponse>(response.body) shouldMatchSnapshot this
    }

    should("call endpoint to fetch today's routes") {
        val response: APIGatewayProxyResponseEvent = handler.handleRequest(
            context = null,
            event = APIGatewayProxyRequestEvent()
                .withHttpMethod(HttpMethod.GET.name())
                .withPath("/v1/parcels/today/routes"),
        )

        decodeFromString<GetRoutesResponse>(response.body)
            .routes
            .map { route ->
                route.copy(stops = route.stops.map { it.copy(id = StopId("REDACTED")) })
            }
            .shouldMatchSnapshot(this)
    }

    should("return a failed response") {
        val localHandler = Handler(
            apiContext = ApiContext(
                parcelRepository = mockk {
                    every { addParcel(any()) } returns Result.failure(RuntimeException("Exception adding parcel"))
                },
            ),
        )
        val response: APIGatewayProxyResponseEvent = localHandler.handleRequest(
            context = null,
            event = APIGatewayProxyRequestEvent()
                .withBody(
                    encodeToString(
                        CreateParcelRequest(
                            id = ParcelId("P001"),
                            weight = 2.3,
                            postCode = PostCode("0465"),
                            address = "123 Main St",
                            status = ParcelStatus.IN_TRANSIT,
                        ),
                    ),
                )
                .withHttpMethod(HttpMethod.POST.name())
                .withPath("/v1/parcels"),
        )

        decodeFromString<ErrorResponse>(response.body).error shouldBe ErrorCode.FAILED_TO_CREATE_PARCEL
    }
})
