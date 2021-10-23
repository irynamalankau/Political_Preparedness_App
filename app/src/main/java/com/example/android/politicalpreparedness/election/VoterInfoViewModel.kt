package com.example.android.politicalpreparedness.election

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.network.models.Division
import com.example.android.politicalpreparedness.network.models.VoterInfoResponse
import kotlinx.coroutines.launch

class VoterInfoViewModel(private val repository: ElectionsRepositoryImplementation) : ViewModel() {

    private val _voterInfo = MutableLiveData<VoterInfoResponse>()
    val voterInfo: LiveData<VoterInfoResponse>
        get() = _voterInfo

    private val _votingLocationsUrl = MutableLiveData<String?>()
    val votingLocationUrl: LiveData<String?>
        get() = _votingLocationsUrl


    private val _ballotInformationUrl = MutableLiveData<String?>()
    val ballotInformationUrl: LiveData<String?>
        get() = _ballotInformationUrl


    fun getVoterInfo(electionId: Int, address: String) {
        viewModelScope.launch {
            try{
                _voterInfo.value = repository.getVoterInfo(electionId, address)

            } catch (e: Exception){
                e.printStackTrace()
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


    //TODO: add following

}


