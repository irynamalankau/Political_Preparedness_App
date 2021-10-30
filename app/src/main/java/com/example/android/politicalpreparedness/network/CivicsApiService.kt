package com.example.android.politicalpreparedness.network


import com.example.android.politicalpreparedness.network.models.Address
import com.example.android.politicalpreparedness.network.models.ElectionResponse
import com.example.android.politicalpreparedness.network.models.RepresentativeResponse
import com.example.android.politicalpreparedness.network.models.VoterInfoResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface CivicsApiService {
    //Add elections API Call
    @GET("elections")
    suspend fun getElections():ElectionResponse

    //Add voterinfo API Call
    @GET("voterinfo")
    suspend fun getVoterInfo(
            @Query("address") address: String,
            @Query("electionId") electionId: Int
    ) : VoterInfoResponse

    //Add representatives API Call
    @GET("representatives")
    suspend fun getRepresentatives(
            @Query("address") address: String
    ) : RepresentativeResponse

}


