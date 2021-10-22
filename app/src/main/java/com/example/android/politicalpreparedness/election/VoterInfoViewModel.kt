package com.example.android.politicalpreparedness.election

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.database.ElectionDao
import com.example.android.politicalpreparedness.network.models.Division
import com.example.android.politicalpreparedness.network.models.Election
import kotlinx.coroutines.launch

class VoterInfoViewModel(private val dataSource: ElectionsRepository, private val electionId: Int, val division: Division) : ViewModel() {

    private val _election = MutableLiveData<Election>()
    val election: LiveData<Election>
        get() = _election

    private lateinit var votingLocation: String

    private lateinit var ballotInformation: String

    init {
        fetchVoterInfo()
    }


    private fun fetchVoterInfo() {
        viewModelScope.launch {
            try{
                val voterInfoResponse = dataSource.getVoterInfo(electionId, division)

                _election.value = voterInfoResponse?.election

                votingLocation = voterInfoResponse?.state?.get(0)?.electionAdministrationBody?.electionInfoUrl ?: ""
                ballotInformation = voterInfoResponse?.state?.get(0)?.electionAdministrationBody?.ballotInfoUrl ?: ""

            } catch (e: Exception){
                e.printStackTrace()
            }
        }

    }

}


