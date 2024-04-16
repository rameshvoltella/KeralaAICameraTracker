package com.ramzmania.aicammvd.geofencing

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent

class GeofenceBroadcastReceiverTwo: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val geofencingEvent = GeofencingEvent.fromIntent(intent)
        Log.e("GeofenceReceiver", "Geofencing event camed.......")
        Toast.makeText(context,"keri"+geofencingEvent,1).show()
if(geofencingEvent==null)
{
    return
}
        if (geofencingEvent!!.hasError()) {
            Log.e("GeofenceReceiver", "Geofencing event error")
            return
        }

        val geofenceTransition = geofencingEvent?.geofenceTransition

        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
            geofencingEvent.triggeringGeofences?.forEach {
                Log.i("GeofenceReceiver", "Entering geofence with ID: ${it.requestId}")
                // Trigger your notification here
            }
        }
    }
}