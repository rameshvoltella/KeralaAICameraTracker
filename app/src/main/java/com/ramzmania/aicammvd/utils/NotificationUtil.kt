package com.ramzmania.aicammvd.utils

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
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
        priority: Int,notificationId:Int,channelId: String
    ) {
        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(icon) // Set your own icon
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(priority)
            .setAutoCancel(true)
        if (intent != null) {
            notificationBuilder.setContentIntent(intent)

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

    fun getNotification(
        context: Context,
        stopPendingIntent: PendingIntent,
        contentPendingIntent: PendingIntent,
        dismissPendingIntent: PendingIntent,
        smallIcon: Int,
        actionIcon: Int,
        title: String,
        content: String,channelId: String
    ): Notification {

        val notificationBuilder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder(context, channelId)
        } else {
            Notification.Builder(context)
        }
        return notificationBuilder
            .setContentTitle(title)
            .setContentText(content)
            .setSmallIcon(smallIcon)
            .setOngoing(true)
            .setContentIntent(contentPendingIntent)
            .setDeleteIntent(dismissPendingIntent)// Set the pending intent to launch the main activity when the notification is clicked
//            .addAction(R.mipmap.ic_launcher_round, "Stop", stopServicePendingIntent)
            /// Set ongoing to true to make the notification sticky
            .addAction(
                actionIcon,
                "Stop",
                stopPendingIntent
            )  // Assuming you have an ic_stop drawable
            .build()
    }


}
