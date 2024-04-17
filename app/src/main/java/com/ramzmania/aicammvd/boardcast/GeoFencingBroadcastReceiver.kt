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
        Log.d("GeofenceBroadcastReceiver", "Geofence triggered: ID = before 1111")

        val geofencingEvent = GeofencingEvent.fromIntent(intent!!)
        if (geofencingEvent?.hasError()!!) {
            return
        }
        val extras = intent.extras
        Log.d("hey",extras.toString())
        Log.d("hey1",extras?.getString("lat").toString())
        val location = geofencingEvent.triggeringLocation
        val triggeringGeofences = geofencingEvent.triggeringGeofences

        // Extract the unique ID from each geofence
        Log.d("GeofenceBroadcastReceiver", "Geofence triggered: ID = before 1111")
        var triggeredLocation="Some camera location"

        if(triggeringGeofences!=null) {
            for (geofence in triggeringGeofences) {
//                Log.d("GeofenceBroadcastReceiver", "Geofence triggered: ID = before")

                triggeredLocation= geofence.requestId
//                Log.d("GeofenceBroadcastReceiver", "Geofence triggered: ID = $geofenceId")

                // Handle the geofence event as needed
                // For example, you can notify the user or update the UI
            }
        }
        Log.d("GeofenceBroadcastReceiver", "Geofence triggered: ID = before 1111")

//        val somevalue= geofencingEvent.triggeringGeofences?.get(0)?.requestId
        when (geofencingEvent.geofenceTransition) {
            Geofence.GEOFENCE_TRANSITION_ENTER -> {
                if (extras != null) {
                    showNotification(
                        context!!,
                        "Entering Geofence",
                        triggeredLocation+" Camera Zone"
                    )
                }
            }

            Geofence.GEOFENCE_TRANSITION_EXIT -> {
                if (extras != null)
                    showNotification(
                        context!!,
                        "Exiting Geofence",
                        triggeredLocation+" Camera Zone"
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
            notificationManager.notify(10, notification)
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