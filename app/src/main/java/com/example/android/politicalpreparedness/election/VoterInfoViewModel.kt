package com.example.android.politicalpreparedness.election

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.network.models.Division
import com.example.android.politicalpreparedness.network.models.VoterInfoResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VoterInfoViewModel(private val repository: ElectionsRepository) : ViewModel() {

    private val _voterInfo = MutableLiveData<VoterInfoResponse>()
    val voterInfo: LiveData<VoterInfoResponse>
        get() = _voterInfo

    private val _votingLocationsUrl = MutableLiveData<String?>()
    val votingLocationUrl: LiveData<String?>
        get() = _votingLocationsUrl


    private val _ballotInformationUrl = MutableLiveData<String?>()
    val ballotInformationUrl: LiveData<String?>
        get() = _ballotInformationUrl


    fun getVoterInfo(division: Division, electionId: Int) {
        var address = division.country
        if (division.state.isNotEmpty()) address += ",${division.state}"
        Log.d(TAG, address)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val response = repository.getVoterInfo(address, electionId)
                    _voterInfo.postValue(response)
                    Log.d(TAG, "${response?.election?.name}")

                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.d(TAG, "Can't get voterInfo")
                }

            }
        }


    }

    //Navigation to URLs
    fun navigateToVotingLocations() {
        _votingLocationsUrl.value = _voterInfo.value?.state?.get(0)?.electionAdministrationBody?.electionInfoUrl ?: ""
    }

    fun votingLocationsNavigated() {
        _votingLocationsUrl.value = null
    }

    fun navigateToBallotInformation() {
        _ballotInformationUrl.value = _voterInfo.value?.state?.get(0)?.electionAdministrationBody?.ballotInfoUrl ?: ""
    }

    fun ballotInformationNavigated() {
        _ballotInformationUrl.value = null
    }


    companion object {
        private const val TAG = "VoterInfoViewModel"
    }
    //TODO: add following

}


