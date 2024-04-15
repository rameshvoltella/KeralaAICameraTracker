package com.ramzmania.aicammvd.service
import android.Manifest
import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.ramzmania.aicammvd.R
import com.ramzmania.aicammvd.boardcast.StopServiceReceiver
import com.ramzmania.aicammvd.data.ContextModule
import com.ramzmania.aicammvd.utils.LocationSharedFlow
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AiCameraLocationUpdateService: Service() {
    private lateinit var locationClient: FusedLocationProviderClient
    @Inject
    lateinit var contextModule: ContextModule  // Context is injected

    // New way to create a LocationRequest using LocationRequest.Builder
    private val locationRequest: LocationRequest = LocationRequest.Builder(
        Priority.PRIORITY_HIGH_ACCURACY, 10000L) // Interval in milliseconds
        .setMinUpdateDistanceMeters(10f)         // Minimum distance in meters
        .setGranularity(Granularity.GRANULARITY_PERMISSION_LEVEL)
        .setWaitForAccurateLocation(true)
        .build()

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        createNotificationChannel()
        startForeground(1, getNotification())

        locationClient = LocationServices.getFusedLocationProviderClient(this)
        startLocationUpdates()

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
        locationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
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
         LocationSharedFlow.locationFlow.tryEmit(location)
//        Log.d("LocationService", "Emission successful:"+ location.latitude+"<>"+location.longitude)
//        Toast.makeText(applicationContext,"Konanana"+location.latitude+"Long:"+location.longitude,1).show()
    }

    private fun getNotification(): Notification {
        val stopIntent = Intent(this, StopServiceReceiver::class.java)
        val stopPendingIntent = PendingIntent.getBroadcast(
            this, 0, stopIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        val notificationBuilder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder(this, CHANNEL_ID)
        } else {
            Notification.Builder(this)
        }
        return notificationBuilder
            .setContentTitle("Location Service")
            .setContentText("Tracking location...")
            .setSmallIcon(R.drawable.snapchatlogo)
            .setOngoing(true) // Set ongoing to true to make the notification sticky
            .addAction(R.drawable.ic_livevideo_doubt, "Stop", stopPendingIntent)  // Assuming you have an ic_stop drawable
            .build()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        locationClient.removeLocationUpdates(locationCallback)
    }

    companion object {
        private const val CHANNEL_ID = "ForegroundServiceChannel"
    }
}