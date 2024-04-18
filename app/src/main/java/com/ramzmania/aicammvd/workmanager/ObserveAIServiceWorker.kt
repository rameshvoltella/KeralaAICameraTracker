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
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.ramzmania.aicammvd.R
import com.ramzmania.aicammvd.service.AiCameraLocationUpdateService
import com.ramzmania.aicammvd.utils.PreferencesUtil
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ObserveAIServiceWorker(context: Context,workerParameters: WorkerParameters):Worker(context,workerParameters) {
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
        createNotificationChannel()

        val dateFormat = SimpleDateFormat("dd-MMM @ HH:mm a", Locale.getDefault())
        val dateTime = dateFormat.format(Date())

        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_livevideo_doubt) // replace with your own drawable
            .setContentTitle("WorkManager Notification")
            .setContentText("Time: $dateTime")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        if (ActivityCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            return
        }
        NotificationManagerCompat.from(applicationContext).notify(NOTIFICATION_ID, notification)
    }

    private fun createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val name = "Notifications"
            val descriptionText = "Channel for Work Manager Notifications"
            val importance = android.app.NotificationManager.IMPORTANCE_DEFAULT
            val channel = android.app.NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            val notificationManager: android.app.NotificationManager =
                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}