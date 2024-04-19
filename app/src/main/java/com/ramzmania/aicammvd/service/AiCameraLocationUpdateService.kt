package com.ramzmania.aicammvd.service

import android.Manifest
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Granularity
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.ramzmania.aicammvd.R
import com.ramzmania.aicammvd.boardcast.StopServiceReceiver
import com.ramzmania.aicammvd.data.ContextModule
import com.ramzmania.aicammvd.data.local.LocalRepository
import com.ramzmania.aicammvd.geofencing.createGeofenceList
import com.ramzmania.aicammvd.geofencing.findNearestCameras
import com.ramzmania.aicammvd.geofencing.playNotificationSound
import com.ramzmania.aicammvd.geofencing.setBatchGeoFencing
import com.ramzmania.aicammvd.ui.screens.home.HomeActivity
import com.ramzmania.aicammvd.utils.Constants
import com.ramzmania.aicammvd.utils.Constants.CHANNEL_ID
import com.ramzmania.aicammvd.utils.Constants.CONTENT_PENDING_INTENT_ID
import com.ramzmania.aicammvd.utils.Constants.DISMISS_PENDING_INTENT_ID
import com.ramzmania.aicammvd.utils.Constants.STOP_PENDING_INTENT_ID
import com.ramzmania.aicammvd.utils.LocationSharedFlow
import com.ramzmania.aicammvd.utils.NotificationUtil
import com.ramzmania.aicammvd.utils.PreferencesUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class AiCameraLocationUpdateService : Service() {

    private lateinit var locationClient: FusedLocationProviderClient

    @Inject
    lateinit var contextModule: ContextModule

    @Inject
    lateinit var localRepository: LocalRepository

    // Context is injected
    private val serviceScope = CoroutineScope(Dispatchers.IO) // Runs tasks in the background

    private val TAG = "AI SERVICE"

    private var memorystatus: String? = ""

    // New way to create a LocationRequest using LocationRequest.Builder
    private val locationRequest: LocationRequest = LocationRequest.Builder(
        Priority.PRIORITY_HIGH_ACCURACY, 3600000L
    ) // Interval in milliseconds
        .setMinUpdateDistanceMeters(20000f)         // Minimum distance in meters
        .setGranularity(Granularity.GRANULARITY_PERMISSION_LEVEL)
        .setWaitForAccurateLocation(true)
        .build()

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        PreferencesUtil.setString(applicationContext, "", "sts")

        NotificationUtil.createNotificationChannel(applicationContext, CHANNEL_ID,
            NotificationManager.IMPORTANCE_DEFAULT)
        startForeground(Constants.FOREGROUND_ID, getNotification())

        locationClient = LocationServices.getFusedLocationProviderClient(this)
        startLocationUpdates()
        PreferencesUtil.setServiceRunning(this, true)
        LocationSharedFlow.serviceStopStatus.tryEmit(false)
        startedService()
        playNotificationSound(applicationContext, R.raw.loco)

        return START_STICKY
    }

    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        locationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )

    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            locationResult.locations.forEach { location ->
                handleNewLocation(location)
            }
        }
    }

    private fun handleNewLocation(location: Location) {
        // Handle your location update logic here
        Log.d("Location Flow Update", "Lat: passing")

//        val locationData = Pair(location.latitude, location.longitude)
//        LocationSharedFlow.locationFlow.tryEmit(location)
//        Log.d("LocationService", "Emission successful:"+ location.latitude+"<>"+location.longitude)
//        Toast.makeText(applicationContext,"Konanana"+location.latitude+"Long:"+location.longitude,1).show()
        serviceScope.launch {
            localRepository.requestCameraLocation().collect {

//                Log.d("kona","collected")
                if (location != null) {
                    val nearestCameraList =
                        it.data?.responseList?.findNearestCameras(
                            location.latitude,
                            location.longitude
                        )
//                Log.d("kona","collected"+nearestCameraList?.size)
                    if (nearestCameraList != null) {

                        val updatedCameraList = createGeofenceList(nearestCameraList!!)

                        setBatchGeoFencing(applicationContext, updatedCameraList)
                    }
//                Log.d("kona","collected"+nearestCameraList.size)
                }
            }
        }
    }

    private fun getNotification(): Notification {
        val stopIntent = Intent(this, StopServiceReceiver::class.java)
        val stopPendingIntent = PendingIntent.getBroadcast(
            this, STOP_PENDING_INTENT_ID, stopIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Create an intent to launch your main activity
        val mainActivityIntent = Intent(this, HomeActivity::class.java)
        mainActivityIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(
            this,
            CONTENT_PENDING_INTENT_ID,
            mainActivityIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        // Create an intent to dismiss

        val dismissIntent = Intent(applicationContext.packageName+".ACTION_NOTIFICATION_DISMISSED")
        val pendingDismissIntent = PendingIntent.getBroadcast(
            this,
            DISMISS_PENDING_INTENT_ID,
            dismissIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationUtil.getNotification(applicationContext,stopPendingIntent,
            pendingIntent,pendingDismissIntent,R.drawable.red_location,R.drawable.stop,
            "Location Service","Tracking location...",
            CHANNEL_ID)

    }


    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        when (level) {
            TRIM_MEMORY_BACKGROUND -> {
//                PreferencesUtil.setServiceRunning(applicationContext, false)
                memorystatus = memorystatus + "<><>" + "TRIM_MEMORY_BACKGROUND"
                saveMeoryValue(memorystatus)
//                Log.d(
//                    TAG,
//                    "UI hidden and not on foreground but service is running"
//                )

            }

            TRIM_MEMORY_MODERATE -> {
                PreferencesUtil.setServiceRunning(applicationContext, false)

                memorystatus = memorystatus + "<><>" + "TRIM_MEMORY_MODERATE"
                saveMeoryValue(memorystatus)

                Log.d(
                    TAG,
                    "Device runs low on memory and actively running processes (like this service) should trim their memory usage"
                )

            }

            TRIM_MEMORY_COMPLETE -> {
                PreferencesUtil.setServiceRunning(applicationContext, false)
                memorystatus = memorystatus + "<><>" + "TRIM_MEMORY_COMPLETE"
                saveMeoryValue(memorystatus)

                Log.d(
                    TAG,
                    "The process is on the LRU list and if the system does not recover memory now, it will kill most likely processes, including this service"
                )
                try {
                    locationClient.removeLocationUpdates(locationCallback);
                } catch (ex: Exception) {

                }
            }

            TRIM_MEMORY_RUNNING_CRITICAL -> {
                PreferencesUtil.setServiceRunning(applicationContext, false)

                memorystatus = memorystatus + "<><>" + "TRIM_MEMORY_RUNNING_CRITICAL"
                saveMeoryValue(memorystatus)

                try {
                    locationClient.removeLocationUpdates(locationCallback);
                } catch (ex: Exception) {

                }

            }

            else -> {
                PreferencesUtil.setServiceRunning(applicationContext, false)

                memorystatus = memorystatus + "<><>" + "OTHER value" + level
                saveMeoryValue(memorystatus)

                try {
                    locationClient.removeLocationUpdates(locationCallback);
                } catch (ex: Exception) {

                }

                Log.d(TAG, "Memory level warning: $level")
            }
        }
    }

    private fun saveMeoryValue(memorystatus: String?) {
        PreferencesUtil.setString(applicationContext, memorystatus!!, "sts")
    }

    override fun onDestroy() {
        super.onDestroy()
        PreferencesUtil.setServiceRunning(this, false)
        LocationSharedFlow.serviceStopStatus.tryEmit(true)
//        Log.d("vadada",">>>"+value)
        locationClient.removeLocationUpdates(locationCallback)
        stoppedService()
    }

    companion object {
        var isServiceStarted: Boolean = false
            private set

        fun startedService() {
            isServiceStarted = true
        }

        fun stoppedService() {
            isServiceStarted = false
        }
    }


}