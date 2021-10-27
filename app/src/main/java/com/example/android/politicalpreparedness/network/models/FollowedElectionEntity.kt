package com.example.android.politicalpreparedness.network.models

import androidx.room.Entity
import androidx.room.PrimaryKey

// Table for the followed elections
@Entity(tableName = "followed_election_table")
data class FollowedElectionEntity(
        @PrimaryKey val id: Int
)