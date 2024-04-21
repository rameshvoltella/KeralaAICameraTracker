package com.ramzmania.aicammvd

import android.app.Application
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.ramzmania.aicammvd.workmanager.LocationWorkerFactory
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class AiApplication: Application(), Configuration.Provider {

    @Inject
    lateinit var locationWorkerFactory: LocationWorkerFactory


    override val workManagerConfiguration: Configuration
        get() =Configuration.Builder()
            .setMinimumLoggingLevel(Log.DEBUG)
            .setWorkerFactory(locationWorkerFactory)
            .build()

}