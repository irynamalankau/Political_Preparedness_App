package com.example.android.politicalpreparedness.election

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.network.models.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VoterInfoViewModel(private val repository: ElectionsRepository) : ViewModel() {

    private val _election = MutableLiveData<ElectionVoterInfo>()
    val election: LiveData<ElectionVoterInfo>
        get() = _election

    private val _correspondenceAddress = MutableLiveData<Address>()
    val correspondenceAddress: LiveData<Address>
        get() = _correspondenceAddress

    private val _locationsUrl = MutableLiveData<String>()
    val locationsUrl: LiveData<String>
        get() = _locationsUrl

    private val _ballotInfoUrl = MutableLiveData<String>()
    val ballotInfoUrl: LiveData<String>
        get() = _ballotInfoUrl


    private val _electionInfoUrl = MutableLiveData<String>()
    val electionInfoUrl: LiveData<String>
        get() = _electionInfoUrl

    private val _url = MutableLiveData<String>()
    val url: LiveData<String>
        get() = _url



    fun getVoterInfo(division: Division, electionId: Int) {
        var address = division.country
        if (division.state.isNotEmpty()) address += ",${division.state}"
        Log.d(TAG, address)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val response = repository.getVoterInfo(address, electionId)
                    val voterInfoState = response?.state?.get(0)?.electionAdministrationBody
                    if (response != null) {
                        _election.postValue(response.election)
                        _locationsUrl.postValue(voterInfoState?.electionInfoUrl)
                        _ballotInfoUrl.postValue(voterInfoState?.ballotInfoUrl)
                        _electionInfoUrl.postValue(voterInfoState?.electionInfoUrl)
                        _correspondenceAddress.postValue((voterInfoState?.correspondenceAddress))
                    }


                    Log.d(TAG, "${response?.election?.name}")

                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.d(TAG, "Can't get voterInfo")
                }

            }
        }
    }

    //Navigation to URLs


    fun getUrl(url: String){
        _url.value = url
    }

    //TODO: add following



    companion object {
        private const val TAG = "VoterInfoViewModel"
    }


}


