package com.ramzmania.aicammvd.boardcast

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent


class GeoFencingBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        val geofencingEvent = GeofencingEvent.fromIntent(intent!!)
        if (geofencingEvent?.hasError()!!) {
            return
        }
        val extras = intent.extras
        Log.d("hey",extras.toString())
        Log.d("hey1",extras?.getString("lat").toString())
        when (geofencingEvent.geofenceTransition) {
            Geofence.GEOFENCE_TRANSITION_ENTER -> {
                if (extras != null) {
                    showNotification(
                        context!!,
                        "Entering Geofence",
                        "You entered geofence with latitude ${extras.getString("lat")} longitude ${
                            extras.getString("lon")
                        } radius ${extras.getString("radius")}"
                    )
                }
            }

            Geofence.GEOFENCE_TRANSITION_EXIT -> {
                if (extras != null)
                    showNotification(
                        context!!,
                        "Exiting Geofence",
                        "You exited  geofence with latitude ${extras.getString("lat")} longitude ${
                            extras.getString("lon")
                        } radius ${extras.getString("radius")}"
                    )
            }

            else -> {
                Log.e(
                    "GeofenceReceiver",
                    "Unknown transition type: ${geofencingEvent.geofenceTransition}"
                )
            }
        }
    }

    private fun showNotification(context: Context, title: String, message: String) {
        val channelId = "geofence_channel"
        createNotificationChannel(context, channelId)

        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        val notificationManager = NotificationManagerCompat.from(context)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            notificationManager.notify(1, notification)
        }

    }

    private fun createNotificationChannel(context: Context, channelId: String) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = android.app.NotificationChannel(
                channelId,
                "Geofence Channel",
                android.app.NotificationManager.IMPORTANCE_HIGH
            )
            val notificationManager =
                ContextCompat.getSystemService(context, android.app.NotificationManager::class.java)
            notificationManager?.createNotificationChannel(channel)
        }
    }
}