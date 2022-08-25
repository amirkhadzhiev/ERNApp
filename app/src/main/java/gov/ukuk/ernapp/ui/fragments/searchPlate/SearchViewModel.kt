package gov.ukuk.ernapp.ui.fragments.searchPlate

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import gov.ukuk.ernapp.base.BaseViewModel
import gov.ukuk.ernapp.data.network.Resource
import gov.ukuk.ernapp.data.repository.Repository
import gov.ukuk.ernapp.models.*
import gov.ukuk.ernapp.models.protocol.Protocol
import gov.ukuk.ernapp.models.violation.Violation
import kotlinx.coroutines.launch
import retrofit2.Response

class SearchViewModel(private val repository: Repository) : BaseViewModel(){

    val protocols = MutableLiveData<Resource<MutableList<Protocol>>>()
    val tundukBezGorod = MutableLiveData<Resource<TundukBezGorod>>()
    val carByPlate = MutableLiveData<Resource<CarByPlate>>()
    val violation = MutableLiveData<Resource<Violation>>()

    fun fetchFilteredDataProt(plateNumber: String) = viewModelScope.launch {
        protocols.postValue(handleProtocols(repository.fetchERNAppApiFilteredDataProt(plateNumber)))
    }

    fun getViolationsByPl(plate: Info) = viewModelScope.launch {
        tundukBezGorod.postValue(Resource.Loading())
        tundukBezGorod.postValue(handleTunduk(repository.getERNAppApiViolationsByPl(plate)))
    }

    fun getCarByPlate(plate: InfoGov) = viewModelScope.launch {
        carByPlate.postValue(Resource.Loading())
        carByPlate.postValue(handleCars(repository.getCarByPlate(plate)))
    }

    fun getViolationsByPlateAndPin(plate: String, pin : String) = viewModelScope.launch {
        violation.postValue(handleViolation(repository.getViolatioinsByPlateAndPin(CarPlateAndPin(plate, pin))))
    }


    private fun handleProtocols(response: Response<MutableList<Protocol>>) : Resource<MutableList<Protocol>> {
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

    private fun handleCars(response: Response<CarByPlate>) : Resource<CarByPlate> {
        if (response.isSuccessful){
            response.body()?.let { resultResponse ->
                return Resource.Succes(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleViolation(response: Response<Violation>) : Resource<Violation> {
        if (response.isSuccessful){
            response.body()?.let { resultResponse ->
                return Resource.Succes(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }




}