package com.example.android.politicalpreparedness.election

import com.example.android.politicalpreparedness.database.ElectionDao
import com.example.android.politicalpreparedness.network.CivicsApiService
import com.example.android.politicalpreparedness.repository.Repository
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val electionModule = module{
    viewModel {
        ElectionsViewModel(get() as Repository)
    }

    viewModel {
        VoterInfoViewModel(get() as Repository)
    }

    single {
        provideRepository(get(), get())
    }
}


private fun provideRepository(
        dao: ElectionDao,
        apiService: CivicsApiService
): Repository = RepositoryImpl(dao, apiService)