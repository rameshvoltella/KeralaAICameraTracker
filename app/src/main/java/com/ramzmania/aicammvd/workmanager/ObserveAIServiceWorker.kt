package com.ramzmania.aicammvd.workmanager

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.content.ContextCompat.startForegroundService
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.ramzmania.aicammvd.service.AiCameraLocationUpdateService
import com.ramzmania.aicammvd.utils.PreferencesUtil

class ObserveAIServiceWorker(context: Context,workerParameters: WorkerParameters):Worker(context,workerParameters) {
    override fun doWork(): Result {
        if (isStopped) {
            // Cleanup or prepare to stop work
            PreferencesUtil.setString(applicationContext,"STOPPED","workz")

            return Result.failure()
        }
        PreferencesUtil.setString(applicationContext,"START"+PreferencesUtil.isServiceRunning(applicationContext),"workz")
        if(!PreferencesUtil.isServiceRunning(applicationContext))
        {
            PreferencesUtil.setString(applicationContext,"START>1","workz")

            Intent(applicationContext, AiCameraLocationUpdateService::class.java).also { intent ->
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    PreferencesUtil.setString(applicationContext,"START>2","workz")

                    applicationContext.startForegroundService(intent)
                } else {
                    PreferencesUtil.setString(applicationContext,"START>3","workz")

                    applicationContext.startService(intent)
                }
                //setTackingServiceRunning(true)
            }
        }
        return Result.success()
    }
}