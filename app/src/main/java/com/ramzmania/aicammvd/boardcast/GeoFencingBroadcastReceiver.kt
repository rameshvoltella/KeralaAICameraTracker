package com.ramzmania.aicammvd.boardcast

import android.Manifest
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import androidx.compose.ui.text.toUpperCase
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent
import com.ramzmania.aicammvd.R
import com.ramzmania.aicammvd.geofencing.playNotificationSound
import com.ramzmania.aicammvd.ui.screens.mapview.OsmMapActivity
import com.ramzmania.aicammvd.utils.Constants
import com.ramzmania.aicammvd.utils.Constants.GEOFENCE_PENDING_INTENT_ID
import com.ramzmania.aicammvd.utils.Logger
import com.ramzmania.aicammvd.utils.NotificationUtil
import java.util.Locale

/**
 * Broadcast receiver for handling geofence events.
 */
class GeoFencingBroadcastReceiver : BroadcastReceiver() {
    /**
     * Handles incoming geofence events.
     *
     * @param context The application context.
     * @param intent The intent containing the geofence event.
     */
    override fun onReceive(context: Context?, intent: Intent?) {
        Logger.d("GeofenceBroadcastReceiver - Geofence triggered: ID = before 0000")

        val geofencingEvent = GeofencingEvent.fromIntent(intent!!)
        if (geofencingEvent?.hasError()!!) {
            return
        }
        val extras = intent.extras
        val location = geofencingEvent.triggeringLocation
        val triggeringGeofences = geofencingEvent.triggeringGeofences

        // Extract the unique ID from each geofence
        Logger.d("GeofenceBroadcastReceiver - Geofence triggered: ID = before 1111")
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
        Logger.d("GeofenceBroadcastReceiver - Geofence triggered: ID = before 22222")

//        val somevalue= geofencingEvent.triggeringGeofences?.get(0)?.requestId
        when (geofencingEvent.geofenceTransition) {
            Geofence.GEOFENCE_TRANSITION_ENTER -> {
                if (extras != null) {
                    showNotification(
                        context!!,
                        "ENTERING AI ZONE",
                        "${triggeredLocation.replace("*"," ").uppercase(Locale.getDefault())} Camera Zone",location
                    )
                }
            }

            Geofence.GEOFENCE_TRANSITION_EXIT -> {
                if (extras != null)
                    showNotification(
                        context!!,
                        "EXITING AI ZONE",
                        "${triggeredLocation.replace("*"," ").uppercase(Locale.getDefault())} Camera Zone",location
                    )
            }

            else -> {
                Logger.e(
                    "GeofenceReceiver - Unknown transition type: ${geofencingEvent.geofenceTransition}"
                )
            }
        }
    }
    /**
     * Displays a notification for entering or exiting a geofence zone.
     *
     * @param context The application context.
     * @param title The title of the notification.
     * @param message The message of the notification.
     * @param location The triggering location.
     */
    private fun showNotification(context: Context, title: String, message: String,location: Location?) {
        NotificationUtil.createNotificationChannel(context,Constants.CHANNEL_ID, NotificationManager.IMPORTANCE_HIGH)

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
        intent.putExtra(Constants.INTENT_FROM_GEO,"geofence")

        // Add any extras you want to pass
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        val pendingIntent = PendingIntent.getActivity(
            context,
            0,  // Request code
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            NotificationUtil.showNotification(context,title,message,pendingIntent,R.drawable.ai_camera_marker,NotificationCompat.PRIORITY_DEFAULT,GEOFENCE_PENDING_INTENT_ID,
                Constants.CHANNEL_ID,true)
            playNotificationSound(context, R.raw.notification_sound)
        }

    }



}