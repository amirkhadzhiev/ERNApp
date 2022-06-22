package gov.ukuk.ernapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import gov.ukuk.ernapp.data.network.RemoteDataSource
import gov.ukuk.ernapp.data.network.Resource
import gov.ukuk.ernapp.models.*
import kotlinx.coroutines.Dispatchers

class Repository(private val dataSource: RemoteDataSource){


    fun fetchERNAppApiFilteredDataProt(plateNumber: String): LiveData<Resource<MutableList<Protocol>>> = liveData(Dispatchers.IO){
        emit(Resource.loading(null))
        val response = dataSource.fetchFilteredDataProt(plateNumber)
        emit(response)
    }

    fun getERNAppApiViolationsByPl(plate: Info): LiveData<Resource<TundukBezGorod>> = liveData(Dispatchers.IO){
        emit(Resource.loading(null))
        val response = dataSource.getViolationsByPl(plate)
        emit(response)
    }

    fun fetchERNAppApiFilteredDataByPin(inn: String): LiveData<Resource<MutableList<Protocol>>> = liveData(Dispatchers.IO){
        emit(Resource.loading(null))
        val response = dataSource.fetchFilteredDataByPin(inn)
        emit(response)
    }

    fun getCarsInfoByPin(pin: SendPin): LiveData<Resource<MutableList<CarInfo>>> = liveData(Dispatchers.IO){
        emit(Resource.loading(null))
        val response = dataSource.getCarsByPin(pin)
        emit(response)
    }

//    fun fetchERNAppApiLocationId(id: Int): LiveData<Resource<Protocol>> = liveData(Dispatchers.IO){
//        emit(Resource.loading(null))
//        val response = dataSource.fetchProtocolId(id)
//        emit(response)
//    }

}