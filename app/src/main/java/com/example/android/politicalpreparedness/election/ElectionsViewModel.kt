package com.example.android.politicalpreparedness.election

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.network.ApiStatus
import com.example.android.politicalpreparedness.network.models.Election
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ElectionsViewModel(private val repository: ElectionsRepository) : ViewModel() {

    //Create live data val for upcoming elections
    val upcomingElections: LiveData<List<Election>>
        get() = repository.getCachedElections()

    val followedElections: LiveData<List<Election>>
        get() = repository.getFollowedElections()

    //Store instance of clicked election from recyclerview
    lateinit var clickedElection: Election

    private val _navigateToVoterInfo = MutableLiveData<Boolean>()
    val navigateToVoterInfo: LiveData<Boolean>
        get() = _navigateToVoterInfo

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status

    //Create val and functions to populate live data for upcoming elections from the API and saved elections from local database
    // Create an init block and launch a coroutine to call repository.refreshElectionsList()
    init {
        fetchElections()
    }

    private fun fetchElections() {
        viewModelScope.launch {
            _status.postValue(ApiStatus.LOADING)
                try {
                    repository.refreshElectionsList()
                    _status.postValue(ApiStatus.DONE)


                } catch (e: Exception) {
                    e.printStackTrace()
                    _status.postValue(ApiStatus.ERROR)
            }

        }
    }

    //Create functions to navigate to saved or upcoming election voter info
    fun onElectionClicked(election: Election) {
        clickedElection = election
        _navigateToVoterInfo.value = true
    }

    fun onVoterInfoNavigated() {
        _navigateToVoterInfo.value = false
    }

    companion object{
        const val TAG = "ElectionsViewModel"
    }

}