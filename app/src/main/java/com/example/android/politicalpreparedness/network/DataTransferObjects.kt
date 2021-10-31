package com.example.android.politicalpreparedness.network

import com.example.android.politicalpreparedness.network.models.Election
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
