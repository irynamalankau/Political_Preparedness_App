package com.example.android.politicalpreparedness.network.models

import java.util.*

data class ElectionVoterInfo(
        val id: Int,
        val name: String,
        val electionDay: Date,
        val ocdDivisionId: Division
)
