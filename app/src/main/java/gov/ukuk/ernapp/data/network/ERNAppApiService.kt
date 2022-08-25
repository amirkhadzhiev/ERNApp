package gov.ukuk.ernapp.data.network

import gov.ukuk.ernapp.models.*
import gov.ukuk.ernapp.models.protocol.Protocol
import gov.ukuk.ernapp.models.violation.Violation
import retrofit2.Response
import retrofit2.http.*

interface ERNAppApiService {



    @GET("/api/v1/protocols/find-by-plate/{plateNumber}")
    suspend fun fetchFilteredDataProtocol(@Path("plateNumber") plateNumber: String): Response<MutableList<Protocol>>

    @Headers("Content-Type: application/json")
    @POST("api/v1/tunduk/get-violations-by-plate")
    suspend fun getViolationsByPlate(@Body plate: Info): Response<TundukBezGorod>

    @GET("/api/v1/protocols/find-by-inn/{inn}")
    suspend fun transportByPin(@Path("inn") inn: String): Response<MutableList<Protocol>>

    @Headers("Content-Type: application/json")
    @POST
    suspend fun getTransportByPin(@Url str : String, @Body pin: SendPin): Response<MutableList<CarInfo>>

    @Headers("Content-Type: application/json")
    @POST
    suspend fun getCarByPlate(@Url str : String, @Body govPlate: InfoGov): Response<CarByPlate>

    @Headers("Content-Type: application/json")
    @POST
    suspend fun getViolationsByPlateAndPin(@Url str : String,
                                           @Body info : CarPlateAndPin
    ): Response<Violation>

}