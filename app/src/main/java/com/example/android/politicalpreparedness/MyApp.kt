package com.example.android.politicalpreparedness

import android.app.Application
import com.example.android.politicalpreparedness.database.databaseModule
import com.example.android.politicalpreparedness.election.electionModule

import com.example.android.politicalpreparedness.network.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApp)
            modules(networkModule, electionModule, databaseModule)
        }

    }
}