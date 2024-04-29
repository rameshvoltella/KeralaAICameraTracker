
/**
 * OsmMapActivity: An activity to display a map using OpenStreetMap (OSM) library.
 * This activity integrates Google's Fused Location Provider for user's location tracking.
 * It also includes functionalities to add markers on the map and update UI based on location changes.
 */
package com.ramzmania.aicammvd.ui.screens.mapview


import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Rect
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.ramzmania.aicammvd.R
import com.ramzmania.aicammvd.databinding.MapViewBinding
import com.ramzmania.aicammvd.geofencing.calculateDistance
import com.ramzmania.aicammvd.ui.base.BaseBinderActivity
import com.ramzmania.aicammvd.utils.Constants
import com.ramzmania.aicammvd.utils.MediaPlayerUtil
import com.ramzmania.aicammvd.viewmodel.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.osmdroid.api.IMapController
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapListener
import org.osmdroid.events.ScrollEvent
import org.osmdroid.events.ZoomEvent
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

@AndroidEntryPoint
class OsmMapActivity : BaseBinderActivity<MapViewBinding, HomeViewModel>(), MapListener {

    // Variable to determine if distance should be shown
    private var showDistance = false

    // Map controller
    lateinit var controller: IMapController


    // Location overlay
    lateinit var mMyLocationOverlay: MyLocationNewOverlay

    // Media player utility for audio alerts
    private lateinit var mediaPlayerUtil: MediaPlayerUtil

    // Fused Location Provider client
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    // Location callback for receiving location updates
    var locationCallback: LocationCallback? = null
    override fun getViewModelClass()=HomeViewModel::class.java

    override fun getViewBinding()= MapViewBinding.inflate(layoutInflater)

    override fun observeViewModel() {
    }

    override fun observeActivity() {
        // Load OSMDroid configuration
        Configuration.getInstance().load(
            applicationContext,
            getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE)
        )

        // Set up the map
        binding.osmmap.setTileSource(TileSourceFactory.MAPNIK)
        binding.osmmap.setMultiTouchControls(true)

        // Initialize media player utility
        mediaPlayerUtil = MediaPlayerUtil(this)

        // Initialize Fused Location Provider client
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Initialize map controller
        controller = binding.osmmap.controller

        // Check if intent contains latitude and longitude to show distance
        if (intent.extras!!.containsKey("lat")) {
            showDistance = true
            binding.distanceLl.visibility = View.VISIBLE
        }

        // Initialize location overlay
        mMyLocationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(this), binding.osmmap)
        mMyLocationOverlay.isDrawAccuracyEnabled = true
        mMyLocationOverlay.setPersonAnchor(1f, 1f)

        // Set map center and zoom level
        val startPoint = GeoPoint(intent.extras!!.getDouble("lat"), intent.extras!!.getDouble("long"))
        controller.setCenter(startPoint)
        controller.animateTo(startPoint)
        controller.setZoom(16.5)

        // Add marker at the specified point
        addMarker(binding.osmmap, startPoint)

        // Add map listener
        binding.osmmap.addMapListener(this)

        // Enable location and follow location if coming from specific intent
        if (intent.extras!!.containsKey(Constants.INTENT_FROM_GEO)) {
            mMyLocationOverlay.enableMyLocation()
            mMyLocationOverlay.enableFollowLocation()
        }
    }


    // Map scroll listener

    override fun onScroll(event: ScrollEvent?): Boolean {
        // event?.source?.getMapCenter()
        Log.e("TAG", "onCreate:la ${event?.source?.getMapCenter()?.latitude}")
        Log.e("TAG", "onCreate:lo ${event?.source?.getMapCenter()?.longitude}")
        //  Log.e("TAG", "onScroll   x: ${event?.x}  y: ${event?.y}", )
        return true
    }
    // Map zoom listener
    override fun onZoom(event: ZoomEvent?): Boolean {
        //  event?.zoomLevel?.let { controller.setZoom(it) }


        Log.e("TAG", "onZoom zoom level: ${event?.zoomLevel}   source:  ${event?.source}")
        return false;
    }

    // Add marker to the map
    private fun addMarker(mapView: MapView, point: GeoPoint) {
        val context = mapView.context
        val icon = context.resources.getDrawable(
            R.drawable.ai_camera_marker,
            context.theme
        ) // Load the custom marker drawable

        val marker = Marker(mapView)
        marker.position = point
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        marker.icon = icon // Set the custom icon
        marker.title = "Custom Marker at $point" // Optional: you can set title for marker

        mapView.overlays.add(marker)
        mapView.invalidate() // Refresh the map to display the marker
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            startLocationUpdates()
        }
    }
    // Start location updates
    private fun startLocationUpdates() {
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000)
            .setWaitForAccurateLocation(true)
            .setMinUpdateIntervalMillis(1000)
            .setMaxUpdateDelayMillis(1000)
            .build()


        locationCallback = object : LocationCallback() {
            @SuppressLint("SetTextI18n")
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult ?: return
                for (location in locationResult.locations) {
                    // Calculate speed here using the Location object
                    try {
                        if (showDistance) {
                            binding.distanceTxt.text =
                                "DISTANCE TO CAM : " + String.format(
                                    "%.1f",
                                    calculateDistance(
                                        location.latitude,
                                        location.longitude,
                                        intent.extras!!.getDouble("lat"),
                                        intent.extras!!.getDouble("long")
                                    )
                                ) + " KM"
                        }
                    } catch (ex: Exception) {
                        binding.distanceTxt.text = "error" + ex.printStackTrace()
                    }


                    val speed = location.speed // Speed in meters/second
//                    val speedKmH = speed * 3.6 // Convert speed to km/h
                    // Now you have the speed, you can use it as needed
                    if ((speed * 3.6) > 80) {
                        binding.speedTxt.setBackgroundResource(R.drawable.rounded_overspeed_text_background)
                        if (!mediaPlayerUtil.isPlayingSound()) {
                            mediaPlayerUtil.playSound(R.raw.overspeed)
                        }

                    } else if ((speed * 3.6) < 80 && (speed * 3.6) >= 60) {

                        binding.speedTxt.setBackgroundResource(R.drawable.rounded_warningspeed_text_background)

                    } else {

                        binding.speedTxt.setBackgroundResource(R.drawable.rounded_normalspeed_text_background)

                    }
                    val speedKmH = String.format("%.1f", speed * 3.6)
                    binding.speedTxt.text = "Speed\n $speedKmH Km/H"
                }
            }
        }

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            return
        }
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback!!,
            null /* Looper */
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        // Remove media and location call backs
        try {
            if (mediaPlayerUtil != null) {
                mediaPlayerUtil.stopSound()
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        try{
            stopLocationUpdates()
        }catch (ex:Exception)
        {

        }
    }
    // Stop location updates
    private fun stopLocationUpdates() {
        if (locationCallback != null) {
            if(fusedLocationClient!=null) {
                fusedLocationClient.removeLocationUpdates(locationCallback!!)
            }
            locationCallback = null
        }
    }
}