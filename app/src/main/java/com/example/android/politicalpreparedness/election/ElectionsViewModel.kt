package com.example.android.politicalpreparedness.election

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.network.models.Election
import kotlinx.coroutines.launch


class ElectionsViewModel(application: Application) : ViewModel() {

    //Create database instance
    private val database = ElectionDatabase.getInstance(application)

    //Create repository instance
    private val repository = ElectionsRepository(database)

    //TODO:implement loading status


    //Create live data val for upcoming elections
    val upcomingElections: LiveData<List<Election>>
        get() = repository.getCachedElections()

    private val _navigateToVoterInfo = MutableLiveData<Int>()
    val navigateToVoterInfo
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
    fun onElectionClicked(id: Int) {
        _navigateToVoterInfo.value = id
    }

    fun onVoterInfoNavigated() {
        _navigateToVoterInfo.value = null
    }

}