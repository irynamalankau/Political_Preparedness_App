package com.example.android.politicalpreparedness.election

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.database.ElectionDatabase
import kotlinx.coroutines.launch


class ElectionsViewModel(application: Application) : ViewModel() {

    //Create database instance
    private val database = ElectionDatabase.getInstance(application)

    //Create repository instance
    private val repository = ElectionsRepository(database)

    //Create live data val for upcoming elections
    private val upcomingElections: LiveData<List<Election>>
        get() = repository.getCachedElections()

    //TODO: Create live data val for saved elections

    //Create val and functions to populate live data for upcoming elections from the API and saved elections from local database
    // Create an init block and launch a coroutine to call repository.refreshElectionsList()
    init{
        viewModelScope.launch {
            repository.refreshElectionsList()
        }
    }

    //TODO: Create functions to navigate to saved or upcoming election voter info

}