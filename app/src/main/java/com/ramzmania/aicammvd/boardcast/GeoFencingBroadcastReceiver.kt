package com.ramzmania.aicammvd.boardcast

import android.Manifest
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.media.AudioAttributes
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent
import com.ramzmania.aicammvd.R
import com.ramzmania.aicammvd.geofencing.playNotificationSound
import com.ramzmania.aicammvd.ui.screens.mapview.OsmMapActivity


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
                        triggeredLocation+" Camera Zone",location
                    )
                }
            }

            Geofence.GEOFENCE_TRANSITION_EXIT -> {
                if (extras != null)
                    showNotification(
                        context!!,
                        "Exiting Geofence",
                        triggeredLocation+" Camera Zone",location
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

    private fun showNotification(context: Context, title: String, message: String,location: Location?) {
        val channelId = "geofence_channel"
        createNotificationChannel(context, channelId)
        val intent = Intent(context, OsmMapActivity::class.java)
        if(location!=null)
        {
            intent.putExtra("lat", location.latitude)
            intent.putExtra("long", location.longitude)
        }else
        {
            intent.putExtra("lat", 0.0)
            intent.putExtra("long",0.0)
        }
       // Add any extras you want to pass
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,  // Request code
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.ai_camera_marker)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        val notificationManager = NotificationManagerCompat.from(context)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            notificationManager.notify(999, notification)
            playNotificationSound(context, R.raw.notification_sound)
        }

    }

    private fun createNotificationChannel(context: Context, channelId: String) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            // Create a channel and set the sound
//            val soundUri = Uri.parse("android.resource://" + context.packageName + "/" + R.raw.notification_sound)
//            val audioAttributes = AudioAttributes.Builder()
//                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
//                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
//                .build()

            val audioAttributes = AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build()

//This is where you apply the custom notification sound. The "notification_sound" file resides in the "raw" folder.
//            val sound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + "com.ramzmania.aicammvd" + "/" + R.raw.loco)

            val channel = android.app.NotificationChannel(
                channelId,
                "AI Geofence Channel",
                android.app.NotificationManager.IMPORTANCE_HIGH
            ).apply {
//                setSound(sound, Notification.AUDIO_ATTRIBUTES_DEFAULT)  // Set custom sound and audio attributes
            }
            val notificationManager =
                ContextCompat.getSystemService(context, android.app.NotificationManager::class.java)
            notificationManager?.createNotificationChannel(channel)
//            playNotificationSound(context, R.raw.notification_sound)
        }
    }

}