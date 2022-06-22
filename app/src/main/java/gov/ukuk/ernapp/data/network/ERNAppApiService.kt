package gov.ukuk.ernapp.data.network

import gov.ukuk.ernapp.models.*
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
    @POST("http://10.145.80.92:5002/api/Values/transportByPin")
    suspend fun getTransportByPin(@Body pin: SendPin): Response<MutableList<CarInfo>>



}