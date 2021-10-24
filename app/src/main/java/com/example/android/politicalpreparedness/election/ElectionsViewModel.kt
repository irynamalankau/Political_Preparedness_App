package com.example.android.politicalpreparedness.election

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.network.models.Election
import kotlinx.coroutines.launch


class ElectionsViewModel(private val repository: ElectionsRepository) : ViewModel() {

    //TODO:implement loading status


    //Create live data val for upcoming elections
    val upcomingElections: LiveData<List<Election>>
        get() = repository.getCachedElections()

    //Store instance of clicked election from recyclerview
    lateinit var clickedElection: Election

    private val _navigateToVoterInfo = MutableLiveData<Boolean>()
    val navigateToVoterInfo: LiveData<Boolean>
        get() = _navigateToVoterInfo



    //TODO: Create live data val for saved elections

    //Create val and functions to populate live data for upcoming elections from the API and saved elections from local database
    // Create an init block and launch a coroutine to call repository.refreshElectionsList()
    init{
        fetchElections()
    }

    private fun fetchElections(){
        viewModelScope.launch {
            try{
                repository.refreshElectionsList()

            }
            catch (e: Exception){
                e.printStackTrace()
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

}