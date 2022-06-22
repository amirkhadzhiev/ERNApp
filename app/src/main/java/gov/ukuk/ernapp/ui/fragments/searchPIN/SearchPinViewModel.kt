package gov.ukuk.ernapp.ui.fragments.searchPIN

import androidx.lifecycle.LiveData
import gov.ukuk.ernapp.base.BaseViewModel
import gov.ukuk.ernapp.data.network.Resource
import gov.ukuk.ernapp.data.repository.Repository
import gov.ukuk.ernapp.models.CarInfo
import gov.ukuk.ernapp.models.Protocol
import gov.ukuk.ernapp.models.SendPin

class SearchPinViewModel (private val repository: Repository) : BaseViewModel() {

    fun fetchFilteredDataByPin(inn: String): LiveData<Resource<MutableList<Protocol>>> {
        return repository.fetchERNAppApiFilteredDataByPin(inn)
    }

    fun getCarsInfoByPin(pin: SendPin): LiveData<Resource<MutableList<CarInfo>>> {
        return repository.getCarsInfoByPin(pin)
    }
}