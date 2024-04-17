package com.ramzmania.aicammvd.geofencing

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import com.ramzmania.aicammvd.boardcast.GeoFencingBroadcastReceiver
import com.ramzmania.aicammvd.data.dto.cameralist.CameraData
import com.ramzmania.aicammvd.utils.PreferencesUtil
import java.util.Locale

fun createGeofenceList(cameraDataList: List<CameraData>): List<Geofence> {
    return cameraDataList.map { data ->
        Log.d("unique",">>>"+data.location.replace(" ","").lowercase(Locale.getDefault()))
        Geofence.Builder()
            .setRequestId(data.location.lowercase(Locale.getDefault()))  // Unique identifier for this geofence
            .setCircularRegion(
                data.latitude,
                data.longitude,
                100f  // Radius in meters, adjust as necessary
            )
            .setExpirationDuration(Geofence.NEVER_EXPIRE)// Geofence does not automatically expire
            .setLoiteringDelay(5000)
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER or Geofence.GEOFENCE_TRANSITION_DWELL or Geofence.GEOFENCE_TRANSITION_EXIT)  // Trigger on enter and exit
            .build()
    }
}


fun List<CameraData>.findNearestCameras(currentLat: Double, currentLong: Double): List<CameraData> {

    Log.d("location we got","locala"+currentLat+"<>"+currentLong)
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

    removeAllGeofences(context)
    val geofencingClient = LocationServices.getGeofencingClient(context)
    val geofencingRequest = GeofencingRequest.Builder()
        .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
        .addGeofences(updatedCameraList)
        .build()

//    val intent = Intent(context, GeofenceBroadcastReceiver::class.java)
//    val geofencePendingIntent: PendingIntent = PendingIntent.getBroadcast(context, 250, intent,
//        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)



    val broadcastintent = Intent(context, GeoFencingBroadcastReceiver::class.java)

    val bundle = Bundle()
//    Log.d(
//        "hey1234",
//        geoLocation.latitude + " " + geoLocation.longitude + " " + geoLocation.radius
//    )
//    bundle.putString("lat", updatedCameraList.ge)
//    bundle.putString("lon", geoLocation.longitude)
//    bundle.putString("radius", geoLocation.radius)
    broadcastintent.putExtras(bundle)
    val pendingIntent = getGeofencePendingIntent(context,broadcastintent)

    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
        geofencingClient.addGeofences(geofencingRequest, pendingIntent).run {
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

fun removeAllGeofences(context: Context) {
    val id =    PreferencesUtil.getPendingIntentId(context)
Log.d("checkingid","removing"+id)
    val removeIntent = Intent(context, GeoFencingBroadcastReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(context, id, removeIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

    val geofencingClient: GeofencingClient = LocationServices.getGeofencingClient(context)

    geofencingClient.removeGeofences(pendingIntent).run {
        addOnSuccessListener {
            // Log success or handle it according to your need
            println("All geofences successfully removed.")
        }
        addOnFailureListener {
            // Log failure or handle the error accordingly
            println("Failed to remove geofences")
        }
    }
}


private fun getGeofencePendingIntent(context: Context,broadcastintent: Intent): PendingIntent {
 val id=(Math.random() * 1000 + 1).toInt()
    Log.d("checkingid","adding"+id)

    PreferencesUtil.setPendingIntentId(context,id)
    return PendingIntent.getBroadcast(
        context,id
        ,
        broadcastintent,
        PendingIntent.FLAG_MUTABLE
    )
}


