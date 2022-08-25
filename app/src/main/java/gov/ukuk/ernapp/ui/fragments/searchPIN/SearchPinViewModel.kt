package gov.ukuk.ernapp.ui.fragments.searchPIN

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import gov.ukuk.ernapp.base.BaseViewModel
import gov.ukuk.ernapp.data.network.Resource
import gov.ukuk.ernapp.data.repository.Repository
import gov.ukuk.ernapp.models.CarInfo
import gov.ukuk.ernapp.models.Info
import gov.ukuk.ernapp.models.SendPin
import gov.ukuk.ernapp.models.TundukBezGorod
import gov.ukuk.ernapp.models.protocol.Protocol
import kotlinx.coroutines.launch
import retrofit2.Response

class SearchPinViewModel (private val repository: Repository) : BaseViewModel() {
    val protocols = MutableLiveData<Resource<MutableList<Protocol>>>()
    val carInfos = MutableLiveData<Resource<MutableList<CarInfo>>>()
    val tundukBezGorod = MutableLiveData<Resource<TundukBezGorod>>()


    fun fetchFilteredDataByPin(inn: String) = viewModelScope.launch {
        protocols.postValue(Resource.Loading())
        protocols.postValue(handleProtocols(repository.fetchERNAppApiFilteredDataByPin(inn)))

    }

    fun getViolationsByPl(plate: Info) = viewModelScope.launch {
        tundukBezGorod.postValue(Resource.Loading())
        tundukBezGorod.postValue(handleTunduk(repository.getERNAppApiViolationsByPl(plate)))
    }

    fun getCarsInfoByPin(pin: SendPin) = viewModelScope.launch {
        carInfos.postValue(Resource.Loading())
        carInfos.postValue( handleCarInfos(repository.getCarsInfoByPin(pin)))
    }


    private fun handleProtocols(response: Response<MutableList<Protocol>>) : Resource<MutableList<Protocol>> {
        if (response.isSuccessful){
            response.body()?.let { resultResponse ->
                return Resource.Succes(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleCarInfos(response: Response<MutableList<CarInfo>>) : Resource<MutableList<CarInfo>> {
        if (response.isSuccessful){
            response.body()?.let { resultResponse ->
                return Resource.Succes(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
    private fun handleTunduk(response: Response<TundukBezGorod>) : Resource<TundukBezGorod> {
        if (response.isSuccessful){
            response.body()?.let { resultResponse ->
                return Resource.Succes(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }
}