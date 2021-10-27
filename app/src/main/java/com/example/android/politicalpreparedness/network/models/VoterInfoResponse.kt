package com.example.android.politicalpreparedness.network.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class VoterInfoResponse (
        val election: ElectionVoterInfo,
        val normalizedInput: NormalizedInput,
        //val pollingLocations: String? = null, //TODO: Future Use
        //val contests: String? = null, //TODO: Future Use*/
        val state: List<State>?,
        val electionElectionOfficials: List<ElectionOfficial>? = null,
        val kind: String

)