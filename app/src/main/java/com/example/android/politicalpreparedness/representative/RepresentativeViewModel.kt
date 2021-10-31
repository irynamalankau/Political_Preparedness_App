package com.example.android.politicalpreparedness.representative

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.repository.Repository
import com.example.android.politicalpreparedness.network.ApiStatus
import com.example.android.politicalpreparedness.network.models.Address
import com.example.android.politicalpreparedness.representative.model.Representative
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RepresentativeViewModel(private val repository: Repository) : ViewModel() {

    //Establish live data for representatives
    private val _representatives = MutableLiveData<List<Representative>>()
    val representatives: LiveData<List<Representative>>
        get() = _representatives

    //variable gets address by the user typing or from geoCodeLocation()
    var address: Address = Address("", "", "", "", "")

    //alert messages for the user
    private val _messageResource = MutableLiveData<Int>()
    val messageResource: LiveData<Int>
        get() = _messageResource

    //api load status
    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    /**
     *  The following code will prove helpful in constructing a representative from the API. This code combines the two nodes of the RepresentativeResponse into a single official :

    val (offices, officials) = getRepresentativesDeferred.await()
    _representatives.value = offices.flatMap { office -> office.getRepresentatives(officials) }

    Note: getRepresentatives in the above code represents the method used to fetch data from the API
    Note: _representatives in the above code represents the established mutable live data housing representatives

     */

    //Create function to fetch representatives from API from a provided address
    fun fetchRepresentatives() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                if (isValidAddress()) {
                    _status.postValue(ApiStatus.LOADING)
                    try {
                        val (offices, officials) = repository.getRepresentatives(address)
                        _representatives.postValue(offices.flatMap { office ->
                            office.getRepresentatives(officials)
                        })
                        _status.postValue(ApiStatus.DONE)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        _status.postValue(ApiStatus.ERROR)
                    }
                }
            }
        }
    }


    private fun isValidAddress(): Boolean {
        return when {
            address.line1.trim().isEmpty() -> {
                _messageResource.postValue(R.string.provide_line1)
                false
            }
            address.city.trim().isEmpty() -> {
                _messageResource.postValue(R.string.provide_city)
                false
            }
            address.zip.trim().isEmpty() -> {
                _messageResource.postValue(R.string.provide_zip)
                false
            }
            else -> true
        }
    }

}
