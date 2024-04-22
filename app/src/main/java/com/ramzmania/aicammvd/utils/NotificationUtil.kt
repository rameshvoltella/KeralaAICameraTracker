package com.ramzmania.aicammvd.utils

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.ramzmania.aicammvd.R
import com.ramzmania.aicammvd.boardcast.NotificationDismissReceiver
import com.ramzmania.aicammvd.boardcast.stopAiTrackerPendingIntent
import com.ramzmania.aicammvd.utils.Constants.CHANNEL_DESCRIPTION
import com.ramzmania.aicammvd.utils.Constants.CHANNEL_ID
import com.ramzmania.aicammvd.utils.Constants.CHANNEL_NAME

object NotificationUtil {


    /**
     * Call this method from your application's onCreate() to setup the notification channel
     */
    fun createNotificationChannel(context: Context, channelId: String,importance:Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                CHANNEL_NAME,
                importance
            ).apply {
                description = CHANNEL_DESCRIPTION
                enableLights(true)
                lightColor = Color.RED
                enableVibration(true)
            }
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(channel)
        }
    }

    /**
     * Method to show a notification
     */
    fun showNotification(
        context: Context,
        title: String,
        content: String,
        intent: PendingIntent?,
        icon: Int,
        priority: Int,notificationId:Int,channelId: String,normalNotification:Boolean
    ) {
        val options = BitmapFactory.Options().apply {
            inSampleSize = 2 // Adjust as needed
        }
        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.red_location) // Set your own icon
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(priority)
        if (intent != null) {
            notificationBuilder.setContentIntent(intent)

        }
        if(normalNotification)
        {
            notificationBuilder.setAutoCancel(true)
            notificationBuilder .setLargeIcon(BitmapFactory.decodeResource(context.resources, icon, options))

        }else
        {
            notificationBuilder.setOngoing(true)


            notificationBuilder.setAutoCancel(false)
            notificationBuilder .addAction(
                    R.drawable.stop,
                    Constants.NOTIFY_STOP_ACTION_TITLE,
                stopAiTrackerPendingIntent(context)
                )

        }

        with(NotificationManagerCompat.from(context)) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {

                return
            }
            notify(notificationId, notificationBuilder.build())
        }
    }




}
