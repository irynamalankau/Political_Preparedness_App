package com.example.android.politicalpreparedness.election

import com.example.android.politicalpreparedness.database.ElectionDao
import com.example.android.politicalpreparedness.network.CivicsApiService
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val electionModule = module{
    viewModel {
        ElectionsViewModel(get() as ElectionsRepositoryImplementation)
    }

    viewModel {
        VoterInfoViewModel(get() as ElectionsRepositoryImplementation)
    }

    single {
        provideElectionRepository(get(), get())
    }
}


private fun provideElectionRepository(
        dao: ElectionDao,
        apiService: CivicsApiService
): ElectionsRepository = ElectionsRepositoryImplementation(dao, apiService)