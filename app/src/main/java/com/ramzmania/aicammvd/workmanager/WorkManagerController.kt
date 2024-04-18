package com.ramzmania.aicammvd.workmanager

import android.content.Context
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.ramzmania.aicammvd.utils.Constants
import java.util.concurrent.TimeUnit

fun startAiServiceWorkManager(context: Context) {
    val periodicWorkRequest =
        PeriodicWorkRequestBuilder<ObserveAIServiceWorker>(5, TimeUnit.MINUTES)
            .addTag(Constants.SERVICE_WORK_MANAGER_TAG)
            .build()

    WorkManager.getInstance(context).enqueue(periodicWorkRequest)
}

fun stopAiServiceWorkManager(context: Context) {

    WorkManager.getInstance(context).cancelAllWorkByTag(Constants.SERVICE_WORK_MANAGER_TAG)
}