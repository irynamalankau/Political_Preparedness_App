package com.example.android.politicalpreparedness.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.android.politicalpreparedness.network.models.ElectionEntity

@Dao
interface ElectionDao {

    // Add insert query
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(elections: List<ElectionEntity>)

    // Add select all election query
    @Query("SELECT * FROM election_table")
    fun getAllElections(): LiveData<List<ElectionEntity>>

    //Add select single election query
    @Query("SELECT * FROM election_table WHERE id =:id")
    suspend fun getElectionById(id: Int): ElectionEntity


    //TODO: Add delete query

    //Add clear query
    @Query("DELETE FROM election_table")
    fun clearElections()


}