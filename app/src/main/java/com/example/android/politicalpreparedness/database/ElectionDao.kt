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
    @Query("SELECT * FROM election_table WHERE NOT id=2000")
    fun getAllElections(): LiveData<List<ElectionEntity>>

    //Query to get followed elections
    @Query("SELECT * FROM election_table WHERE id in (SELECT id FROM followed_election_table) ORDER BY electionDay DESC")
    fun getFollowedElections(): LiveData<List<ElectionEntity>>

    //Add select single election query
    @Query("SELECT * FROM election_table WHERE id =:id")
    suspend fun getElectionById(id: Int): ElectionEntity

    //Add insert followed election id
    @Query("INSERT INTO followed_election_table (id) VALUES(:idElection)")
    suspend fun insertFollowedElection(idElection: Int)

    //Add query to check if election is followed by user
    @Query("SELECT CASE id WHEN NULL THEN 0 ELSE 1 END FROM followed_election_table WHERE id = :idElection")
    fun isElectionFollowed(idElection: Int): Int

    //Delete id of election that user decided to unfollow
    @Query("DELETE FROM followed_election_table WHERE id = :idElection")
    suspend fun deleteFollowedElection(idElection: Int)

    //Add clear query
    @Query("DELETE FROM election_table")
    fun clearElections()


}