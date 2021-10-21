package com.example.android.politicalpreparedness.network

import com.example.android.politicalpreparedness.election.Election
import com.example.android.politicalpreparedness.network.models.ElectionEntity

fun List<ElectionEntity>.asDomainModel(): List<Election> {
    return map {
        Election(
                id = it.id,
                name = it.name,
                electionDay = it.electionDay,
                division = it.division
        )
    }
}

fun List<Election>.asDatabaseModel(): List<ElectionEntity>{
    return map {
        ElectionEntity(
                id = it.id,
                name = it.name,
                electionDay = it.electionDay,
                division = it.division
        )
    }
}