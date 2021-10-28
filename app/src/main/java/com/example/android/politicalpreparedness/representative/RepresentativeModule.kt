package com.example.android.politicalpreparedness.representative

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

    val representativeModule = module {
        viewModel {
            RepresentativeViewModel(get())
        }
    }
