package com.ramzmania.aicammvd

import android.app.Application
import android.util.Log
import androidx.work.Configuration
import com.ramzmania.aicammvd.workmanager.LocationWorkerFactory
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

/**
 * Custom application class for the AI Cam application.
 */
@HiltAndroidApp
class AiApplication: Application(), Configuration.Provider {

    @Inject
    lateinit var locationWorkerFactory: LocationWorkerFactory

    /**
     * Provides the configuration for WorkManager.
     */
    override val workManagerConfiguration: Configuration
        get() =Configuration.Builder()
            .setMinimumLoggingLevel(Log.DEBUG)
            .setWorkerFactory(locationWorkerFactory)
            .build()

}