package com.example.android.politicalpreparedness.election

import androidx.lifecycle.LiveData
import com.example.android.politicalpreparedness.network.models.Division
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.network.models.VoterInfoResponse

interface ElectionsRepository {

    fun getCachedElections(): LiveData<List<Election>>

    suspend fun refreshElectionsList()

    fun getFollowedElections(): LiveData<List<Election>>

    suspend fun getVoterInfo(address: String, electionId: Int): VoterInfoResponse?

    suspend fun isElectionFollowed(electionId: Int): Boolean

    suspend fun followElection(electionId: Int)

    suspend fun deleteFollowedElection(electionId: Int)


}