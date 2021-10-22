package com.example.android.politicalpreparedness.election

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.asDomainModel
import com.example.android.politicalpreparedness.network.models.Division
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.network.models.VoterInfoResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val TAG = "Elections Repository"

class ElectionsRepository(val database: ElectionDatabase) {

    //get all elections from the db
    fun getCachedElections(): LiveData<List<Election>> {
        return Transformations.map(database.electionDao.getAllElections()) {
            it.asDomainModel()
        }
    }

    //get elections list from API and insert into db
    //TODO: need to add filtration by date to show only upcoming elections and remove the old ones
    suspend fun refreshElectionsList() {
        withContext(Dispatchers.IO) {
            try {
                val elections = CivicsApi.retrofitService.getElections().elections

                database.electionDao.insertAll(elections)

                Log.d(TAG, elections.toString())
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    //get Voter Info from API
    //address is division.state
    suspend fun getVoterInfo(electionId: Int, division: Division): VoterInfoResponse? {
        return try {
            CivicsApi.retrofitService.getVoterInfo(division.state, electionId)
        } catch (e: Exception) {
            null
        }
    }
}