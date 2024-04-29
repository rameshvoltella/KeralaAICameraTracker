/**
 * Utility class for managing location updates using the Fused Location Provider API.
 *
 * @param context The application context.
 */
package com.ramzmania.aicammvd.geofencing

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*

class LocationUtils(private val context: Context) {
    private var fusedLocationProviderClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)
    private var locationCallback: LocationCallback? = null

    /**
     * Interface definition for a callback to be invoked when a location update is received or an error occurs.
     */
    interface LocationListener {
        /**
         * Called when a new location update is received.
         *
         * @param location The new location.
         */
        fun onLocationResult(location: Location?)
        /**
         * Called when an error occurs while receiving location updates.
         *
         * @param e The exception representing the error.
         */
        fun onLocationError(e: Exception)
    }

    /**
     * Starts receiving location updates.
     *
     * @param locationListener The listener to receive location updates and errors.
     */
    @SuppressLint("MissingPermission")
    fun startLocationUpdates(locationListener: LocationListener) {
        // Check location permissions
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            locationListener.onLocationError(SecurityException("Location permissions not granted"))
            return
        }
        // Define location request parameters
        val locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 10000
            fastestInterval = 5000
        }

        // Define location callback
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationListener.onLocationResult(locationResult.lastLocation)
            }

            override fun onLocationAvailability(locationAvailability: LocationAvailability) {
                if (!locationAvailability.isLocationAvailable) {
                    locationListener.onLocationError(Exception("Location is not available"))
                }
            }
        }
        // Request location updates
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback!!,
            context.mainLooper
        )
    }

    /**
     * Stops receiving location updates.
     */
    fun stopLocationUpdates() {
        if (locationCallback != null) {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback!!)
            locationCallback = null
        }
    }
}