package gov.ukuk.ernapp.data.network

import gov.ukuk.ernapp.models.CarPlateAndPin
import gov.ukuk.ernapp.models.Info
import gov.ukuk.ernapp.models.InfoGov
import gov.ukuk.ernapp.models.SendPin
import org.koin.dsl.module

val remoteDataSourceModule = module {
    factory { RemoteDataSource(get()) }
}

class RemoteDataSource(private val apiService: ERNAppApiService) {

    suspend fun fetchFilteredDataProt(plateNumber: String) = apiService.fetchFilteredDataProtocol(plateNumber)


    suspend fun getViolationsByPl(plate: Info) = apiService.getViolationsByPlate(plate)


    suspend fun fetchFilteredDataByPin(inn: String) = apiService.transportByPin(inn)


    suspend fun getCarsByPin(pin: SendPin) =  apiService.getTransportByPin("http://10.145.80.92:5002/api/Values/transportByPin",pin)


    suspend fun getCarByPlate(plate: InfoGov) = apiService.getCarByPlate("http://10.111.70.72:8000/api/v1/tunduk/transport-current-info",plate)

    suspend fun getViolationByPlateAndPin(info : CarPlateAndPin) = apiService.getViolationsByPlateAndPin("http://10.111.70.72:8000/api/v1/tunduk/get-violations-by-plate-and-pin",info)


//    suspend fun fetchProtocolId(id: String) = getResult {
//        apiService.fetchProtocolERN(id)
//    }

}