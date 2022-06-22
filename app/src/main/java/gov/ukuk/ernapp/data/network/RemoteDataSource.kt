package gov.ukuk.ernapp.data.network

import gov.ukuk.ernapp.base.BaseDataSource
import gov.ukuk.ernapp.models.Info
import gov.ukuk.ernapp.models.SendPin
import org.koin.dsl.module

val remoteDataSourceModule = module {
    factory { RemoteDataSource(get()) }
}

class RemoteDataSource(private val apiService: ERNAppApiService): BaseDataSource() {

    suspend fun fetchFilteredDataProt(plateNumber: String) = getResult {
        apiService.fetchFilteredDataProtocol(plateNumber)
    }

    suspend fun getViolationsByPl(plate: Info) = getResult {
        apiService.getViolationsByPlate(plate)
    }

    suspend fun fetchFilteredDataByPin(inn: String) = getResult {
        apiService.transportByPin(inn)
    }

    suspend fun getCarsByPin(pin: SendPin) = getResult {
        apiService.getTransportByPin(pin)
    }

//    suspend fun fetchProtocolId(id: String) = getResult {
//        apiService.fetchProtocolERN(id)
//    }

}