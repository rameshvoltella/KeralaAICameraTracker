package com.ramzmania.aicammvd.workmanager

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.startForegroundService
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.ramzmania.aicammvd.R
import com.ramzmania.aicammvd.data.local.LocalRepository
import com.ramzmania.aicammvd.service.AiCameraLocationUpdateService
import com.ramzmania.aicammvd.utils.NotificationUtil
import com.ramzmania.aicammvd.utils.PreferencesUtil
import dagger.assisted.AssistedInject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
@HiltWorker
class ObserveAIServiceWorker @AssistedInject constructor(context: Context, workerParameters: WorkerParameters,localRepository: LocalRepository):Worker(context,workerParameters) {
    companion object {

        const val CHANNEL_ID = "ai_notify_channel"
        const val NOTIFICATION_ID = 778
    }
    override fun doWork(): Result {
        val dateFormat = SimpleDateFormat("dd-MMM @ HH:mm a", Locale.getDefault())
        val dateTime = dateFormat.format(Date())

        if (isStopped) {
            // Cleanup or prepare to stop work
            PreferencesUtil.setString(applicationContext, "STOPPED @ $dateTime ","workz")

            return Result.failure()
        }
        showNotification()
        PreferencesUtil.setString(applicationContext,"START @ $dateTime "+PreferencesUtil.isServiceRunning(applicationContext),"workz")
        if(!PreferencesUtil.isServiceRunning(applicationContext)||!AiCameraLocationUpdateService.isServiceStarted)
        {
            PreferencesUtil.setString(applicationContext,"START>1  @ $dateTime ","workz")

            Intent(applicationContext, AiCameraLocationUpdateService::class.java).also { intent ->
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    PreferencesUtil.setString(applicationContext,"START>2  @ $dateTime ","workz")

                    applicationContext.startForegroundService(intent)
                } else {
                    PreferencesUtil.setString(applicationContext,"START>3  @ $dateTime ","workz")

                    applicationContext.startService(intent)
                }
                //setTackingServiceRunning(true)
            }
        }
        return Result.success()
    }

    private fun showNotification() {
        NotificationUtil.createNotificationChannel(applicationContext, CHANNEL_ID,NotificationCompat.PRIORITY_DEFAULT)

        val dateFormat = SimpleDateFormat("dd-MMM @ HH:mm a", Locale.getDefault())
        val dateTime = dateFormat.format(Date())

        if (ActivityCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            return
        }
        NotificationUtil.showNotification(applicationContext,"WorkManager Notification","Time: $dateTime",null,NotificationCompat.PRIORITY_DEFAULT,R.drawable.red_location,NOTIFICATION_ID,CHANNEL_ID)
    }

}