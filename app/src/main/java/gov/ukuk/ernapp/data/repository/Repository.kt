package gov.ukuk.ernapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import gov.ukuk.ernapp.data.network.RemoteDataSource
import gov.ukuk.ernapp.data.network.Resource
import gov.ukuk.ernapp.models.*
import gov.ukuk.ernapp.models.violation.Violation
import kotlinx.coroutines.Dispatchers

class Repository(private val dataSource: RemoteDataSource){


    suspend fun fetchERNAppApiFilteredDataProt(plateNumber: String) = dataSource.fetchFilteredDataProt(plateNumber)

    suspend fun getERNAppApiViolationsByPl(plate: Info) = dataSource.getViolationsByPl(plate)


    suspend fun fetchERNAppApiFilteredDataByPin(inn: String) = dataSource.fetchFilteredDataByPin(inn)

    suspend fun getCarsInfoByPin(pin: SendPin) = dataSource.getCarsByPin(pin)


    suspend fun getCarByPlate(plate: InfoGov) = dataSource.getCarByPlate(plate)

    suspend fun getViolatioinsByPlateAndPin(info : CarPlateAndPin) = dataSource.getViolationByPlateAndPin(info)


//    fun fetchERNAppApiLocationId(id: Int): LiveData<Resource<Protocol>> = liveData(Dispatchers.IO){
//        emit(Resource.loading(null))
//        val response = dataSource.fetchProtocolId(id)
//        emit(response)
//    }

}