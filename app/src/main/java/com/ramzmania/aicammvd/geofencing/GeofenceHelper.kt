package com.ramzmania.aicammvd.geofencing

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import com.ramzmania.aicammvd.data.dto.cameralist.CameraData

class GeofenceHelper(private val context: Context) {
    private val geofencingClient = LocationServices.getGeofencingClient(context)

    private fun getGeofencingRequest(geofenceList: List<Geofence>): GeofencingRequest {
        return GeofencingRequest.Builder().apply {
            setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            addGeofences(geofenceList)
        }.build()
    }

    private fun getGeofencePendingIntent(): PendingIntent {
        val intent = Intent(context, GeofenceBroadcastReceiverTwo::class.java)
        intent.action = "com.ramzmania.aicammvd.ACTION_GEOFENCE_EVENT" // Make sure this action is unique and consistent.
        return PendingIntent.getBroadcast(context, 134, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
    }

    fun addGeofences(cameraDataList: List<CameraData>) {
        val geofenceList = cameraDataList.map {
            Geofence.Builder()
                .setRequestId(it.uniqueId)
                .setCircularRegion(it.latitude, it.longitude, 70000f) // 100m radius
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .setLoiteringDelay(5000)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER or Geofence.GEOFENCE_TRANSITION_DWELL or Geofence.GEOFENCE_TRANSITION_EXIT)
                .build()
        }

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return // Handle permission check
        }

        geofencingClient.addGeofences(getGeofencingRequest(geofenceList), getGeofencePendingIntent())
            .addOnSuccessListener {
                // Handle success
                Log.d("tada","added")
            }
            .addOnFailureListener {
                // Handle failure
                Log.d("tada","failed")
                it.printStackTrace()

            }
    }
}