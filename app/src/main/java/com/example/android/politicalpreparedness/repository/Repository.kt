package com.example.android.politicalpreparedness.repository

import androidx.lifecycle.LiveData
import com.example.android.politicalpreparedness.network.models.*

interface Repository {

    fun getCachedElections(): LiveData<List<Election>>

    suspend fun refreshElectionsList()

    fun getFollowedElections(): LiveData<List<Election>>

    suspend fun getVoterInfo(address: String, electionId: Int): VoterInfoResponse?

    suspend fun isElectionFollowed(electionId: Int): Boolean

    suspend fun followElection(electionId: Int)

    suspend fun deleteFollowedElection(electionId: Int)

    suspend fun getRepresentatives(address: Address): RepresentativeResponse

}