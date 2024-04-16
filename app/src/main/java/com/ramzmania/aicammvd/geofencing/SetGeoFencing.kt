package com.ramzmania.aicammvd.geofencing

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import com.ramzmania.aicammvd.boardcast.GeofenceBroadcastReceiver
import com.ramzmania.aicammvd.data.dto.cameralist.CameraData

fun createGeofenceList(cameraDataList: List<CameraData>): List<Geofence> {
    return cameraDataList.map { data ->
        Geofence.Builder()
            .setRequestId(data.uniqueId)  // Unique identifier for this geofence
            .setCircularRegion(
                data.latitude,
                data.longitude,
                100f  // Radius in meters, adjust as necessary
            )
            .setExpirationDuration(Geofence.NEVER_EXPIRE)  // Geofence does not automatically expire
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER or Geofence.GEOFENCE_TRANSITION_EXIT)  // Trigger on enter and exit
            .build()
    }
}


fun List<CameraData>.findNearestCameras(currentLat: Double, currentLong: Double): List<CameraData> {
    val currentLocation = Location("").apply {
        latitude = currentLat
        longitude = currentLong
    }

    return sortedBy {
        Location("").apply {
            latitude = it.latitude
            longitude = it.longitude
        }.distanceTo(currentLocation)
    }.take(50)
}

fun setBatchGeoFencing(context: Context, updatedCameraList: List<Geofence>)
{
    val geofencingClient = LocationServices.getGeofencingClient(context)
    val geofencingRequest = GeofencingRequest.Builder()
        .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
        .addGeofences(updatedCameraList)
        .build()

    val intent = Intent(context, GeofenceBroadcastReceiver::class.java)
    val geofencePendingIntent: PendingIntent = PendingIntent.getBroadcast(context, 0, intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
        geofencingClient.addGeofences(geofencingRequest, geofencePendingIntent).run {
            addOnSuccessListener {
                Log.d("Geofence", "Geofences added")
            }
            addOnFailureListener {
                Log.e("Geofence", "Failed to add geofences", it)
            }
        }
    } else {
        // Request permissions here if not already granted
    }
}

fun removeAllGeoFences()
{

}


