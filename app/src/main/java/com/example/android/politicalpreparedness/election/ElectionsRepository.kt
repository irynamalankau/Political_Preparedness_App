package com.example.android.politicalpreparedness.election

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.network.CivicsApi
import com.example.android.politicalpreparedness.network.asDomainModel
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
    suspend fun refreshElectionsList() {
        withContext(Dispatchers.IO) {
            try {
                val response = CivicsApi.retrofitService.getElections()
                val elections = response.elections

                database.electionDao.insertAll(elections)

                Log.d(TAG, elections.toString())
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }
}