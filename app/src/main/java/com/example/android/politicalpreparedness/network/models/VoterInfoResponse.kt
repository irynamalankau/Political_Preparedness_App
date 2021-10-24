package com.example.android.politicalpreparedness.network.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class VoterInfoResponse (
        val election: ElectionEntity,
        val normalizedInput: NormalizedInput,
        //val pollingLocations: String? = null, //TODO: Future Use
        //val contests: String? = null, //TODO: Future Use*/
        val state: List<State>?,
        val kind: String
        //val electionElectionOfficials: List<ElectionOfficial>? = null

)