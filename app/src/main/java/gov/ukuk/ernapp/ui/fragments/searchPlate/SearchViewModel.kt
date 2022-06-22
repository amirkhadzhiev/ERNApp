package gov.ukuk.ernapp.ui.fragments.searchPlate

import androidx.lifecycle.LiveData
import gov.ukuk.ernapp.base.BaseViewModel
import gov.ukuk.ernapp.data.network.Resource
import gov.ukuk.ernapp.data.repository.Repository
import gov.ukuk.ernapp.models.Info
import gov.ukuk.ernapp.models.TundukBezGorod
import gov.ukuk.ernapp.models.Protocol

class SearchViewModel(private val repository: Repository) : BaseViewModel(){


    fun fetchFilteredDataProt(plateNumber: String): LiveData<Resource<MutableList<Protocol>>> {
        return repository.fetchERNAppApiFilteredDataProt(plateNumber)
    }
    fun getViolationsByPl(plate: Info): LiveData<Resource<TundukBezGorod>> {
        return repository.getERNAppApiViolationsByPl(plate)
    }

}