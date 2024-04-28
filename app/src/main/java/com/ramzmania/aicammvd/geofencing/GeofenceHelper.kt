package com.ramzmania.aicammvd.geofencing

import android.Manifest
import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import com.ramzmania.aicammvd.R
import com.ramzmania.aicammvd.boardcast.GeoFencingBroadcastReceiver
import com.ramzmania.aicammvd.data.dto.cameralist.CameraData
import com.ramzmania.aicammvd.utils.LocationSharedFlow
import com.ramzmania.aicammvd.utils.Logger
import com.ramzmania.aicammvd.utils.PreferencesUtil
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun createGeofenceList(cameraDataList: List<CameraData>): List<Geofence> {
    return cameraDataList.map { data ->
//        Log.d("unique",">>>"+data.location.replace(" ","").lowercase(Locale.getDefault()))
        Geofence.Builder()
            .setRequestId(data.location.replace(" ","").lowercase(Locale.getDefault()))  // Unique identifier for this geofence
            .setCircularRegion(
                data.latitude,
                data.longitude,
                500f  // Radius in meters, adjust as necessary
            )
            .setExpirationDuration(Geofence.NEVER_EXPIRE)// Geofence does not automatically expire
            .setLoiteringDelay(5000)
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER or Geofence.GEOFENCE_TRANSITION_DWELL or Geofence.GEOFENCE_TRANSITION_EXIT)  // Trigger on enter and exit
            .build()
    }
}


fun List<CameraData>.findNearestCameras(currentLat: Double, currentLong: Double,showLimited:Boolean): List<CameraData> {

    Logger.d("location we got - locala$currentLat<>$currentLong")
    val currentLocation = Location("").apply {
        latitude = currentLat
        longitude = currentLong
    }
if(showLimited) {
    return sortedBy {
        Location("").apply {
            latitude = it.latitude
            longitude = it.longitude
        }.distanceTo(currentLocation)
    }.take(50)
}else
{
    return sortedBy {
        Location("").apply {
            latitude = it.latitude
            longitude = it.longitude
        }.distanceTo(currentLocation)
    }
}
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

                Logger.d("Geofence - Geofences added")
                try {
//                    getCurrentDate(context)
                }catch (ex:Exception)
                {
//                    PreferencesUtil.setString(context, "exception","timer")

                }
            }
            addOnFailureListener {
                Logger.e("Geofence-Failed to add geofences", it)
//                PreferencesUtil.setString(context, "failed","timer")

            }
        }
    } else {
        // Request permissions here if not already granted
    }
}

fun removeAllGeofences(context: Context) {
    val id =    PreferencesUtil.getPendingIntentId(context)
Logger.d("removeAll-Geofences - removing"+id)
    val removeIntent = Intent(context, GeoFencingBroadcastReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(context, id, removeIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

    val geofencingClient: GeofencingClient = LocationServices.getGeofencingClient(context)

    geofencingClient.removeGeofences(pendingIntent).run {
        addOnSuccessListener {
            // Log success or handle it according to your need
            Logger.d("Geofence - All geofences successfully removed.")
        }
        addOnFailureListener {
            // Log failure or handle the error accordingly
            Logger.d("Geofence - Failed to remove geofences")
        }
    }
}


@SuppressLint("SuspiciousIndentation")
private fun getGeofencePendingIntent(context: Context, broadcastintent: Intent): PendingIntent {
 val id=(Math.random() * 1000 + 1).toInt()
    Logger.d("adding$id")

    PreferencesUtil.setPendingIntentId(context,id)
    return PendingIntent.getBroadcast(
        context,id
        ,
        broadcastintent,
        PendingIntent.FLAG_MUTABLE
    )
}

 fun getCompleteAddressString(context: Context, latitude: Double, longitude: Double) {
    var strAdd = ""
    val geocoder = Geocoder(context, Locale.getDefault())
    try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            geocoder.getFromLocation(latitude,longitude,1,object : Geocoder.GeocodeListener{
                override fun onGeocode(addresses: MutableList<Address>) {
                    addresses?.let {
                        getAddress(addresses)
                    }
                    // code
                }
                override fun onError(errorMessage: String?) {
                    super.onError(errorMessage)

                }

            })
        }else {
            val addresses: List<Address>? = geocoder.getFromLocation(latitude, longitude, 1)
            addresses?.let {
                getAddress(addresses)
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun getAddress(address: List<Address>?) {
    try {
        if (address!!.isNotEmpty()) {
            val returnedAddress: Address = address[0]
            val strReturnedAddress = StringBuilder()

            for (i in 0..returnedAddress.maxAddressLineIndex) {
                strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n")
            }
            val strAdd = strReturnedAddress.toString()
            LocationSharedFlow.geocoderAddressData.tryEmit(strAdd)
        }
    }catch (ex:Exception)
    {
        ex.printStackTrace()
    }
}


fun playNotificationSound(context: Context, notificationSound: Int) {
    val mediaPlayer: MediaPlayer = MediaPlayer.create(context, notificationSound)
    mediaPlayer.start()
    mediaPlayer.setOnCompletionListener { mp ->
        mp.release() // Release media player resources once the sound playback is complete
    }
}

fun getCurrentDate(context: Context) {
    val calendar = Calendar.getInstance()

    // Get day with suffix (e.g., 1st, 2nd, 3rd, 4th, etc.)
    val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
    val daySuffix = when {
        dayOfMonth in 11..13 -> "th" // For 11th, 12th, 13th
        dayOfMonth % 10 == 1 -> "st"
        dayOfMonth % 10 == 2 -> "nd"
        dayOfMonth % 10 == 3 -> "rd"
        else -> "th"
    }

    // Format the date
    val sdf = SimpleDateFormat("d'$daySuffix' MMM @ hh:mm a", Locale.ENGLISH)
    PreferencesUtil.setString(context, sdf.format(calendar.time),"timer")

//    return sdf.format(calendar.time)
}

