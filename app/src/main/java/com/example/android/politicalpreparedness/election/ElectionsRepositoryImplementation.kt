package com.example.android.politicalpreparedness.election

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.android.politicalpreparedness.database.ElectionDao
import com.example.android.politicalpreparedness.network.CivicsApiService
import com.example.android.politicalpreparedness.network.asDomainModel
import com.example.android.politicalpreparedness.network.models.Division
import com.example.android.politicalpreparedness.network.models.Election
import com.example.android.politicalpreparedness.network.models.VoterInfoResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val TAG = "Elections Repository"

class ElectionsRepositoryImplementation(private val dao: ElectionDao, private val apiService: CivicsApiService ): ElectionsRepository {

    //get all elections from the db
    override fun getCachedElections(): LiveData<List<Election>> {
        return Transformations.map(dao.getAllElections()) {
            it.asDomainModel()
        }
    }

    //get elections list from API and insert into db
    //TODO: need to add filtration by date to show only upcoming elections and remove the old ones
    override suspend fun refreshElectionsList() {
        withContext(Dispatchers.IO) {
            try {
                val elections = apiService.getElections().elections

                dao.insertAll(elections)

                Log.d(TAG, elections.toString())
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    //get Voter Info from API
    override suspend fun getVoterInfo(electionId: Int, address: String): VoterInfoResponse? {
        return try {
            apiService.getVoterInfo(address, electionId)
        } catch (e: Exception) {
            null
        }
    }
}