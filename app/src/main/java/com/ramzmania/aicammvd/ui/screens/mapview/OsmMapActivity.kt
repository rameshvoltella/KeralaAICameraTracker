package com.ramzmania.aicammvd.ui.screens.mapview


import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Rect

import android.view.View
import android.view.WindowManager
import androidx.activity.OnBackPressedCallback

import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.ramzmania.aicammvd.R
import com.ramzmania.aicammvd.databinding.MapViewBinding
import com.ramzmania.aicammvd.geofencing.calculateDistance
import com.ramzmania.aicammvd.ui.base.BaseBinderActivity
import com.ramzmania.aicammvd.ui.screens.home.HomeActivity
import com.ramzmania.aicammvd.utils.Constants
import com.ramzmania.aicammvd.utils.Logger
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


/**
 * OsmMapActivity: An activity to display a map using OpenStreetMap (OSM) library.
 * This activity integrates Google's Fused Location Provider for user's location tracking.
 * It also includes functionalities to add markers on the map and update UI based on location changes.
 */
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
    override fun getViewModelClass() = HomeViewModel::class.java

    override fun getViewBinding() = MapViewBinding.inflate(layoutInflater)

    override fun observeViewModel() {
    }

    override fun observeActivity() {
        if (intent.extras!!.containsKey("lat")) {
            showDistance = true
            binding.distanceLl.visibility = View.VISIBLE
        }
        mediaPlayerUtil = MediaPlayerUtil(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        Configuration.getInstance().load(
            applicationContext,
            getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE)
        )
        binding.osmmap.setTileSource(TileSourceFactory.MAPNIK)
        binding.osmmap.mapCenter
        binding.osmmap.setMultiTouchControls(true)
        binding.osmmap.getLocalVisibleRect(Rect())


        mMyLocationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(this), binding.osmmap)
        controller = binding.osmmap.controller


//        mMyLocationOverlay.enableMyLocation()
//        mMyLocationOverlay.enableFollowLocation()
        // Enable location and follow location if coming from specific intent
        if (intent.extras!!.containsKey(Constants.INTENT_FROM_GEO)) {
        mMyLocationOverlay.enableMyLocation()
        mMyLocationOverlay.enableFollowLocation()
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
        mMyLocationOverlay.isDrawAccuracyEnabled = true
        mMyLocationOverlay.setPersonAnchor(1f, 1f)

//        controller.setZoom(6.0)
        val startPoint =
            GeoPoint(intent.extras!!.getDouble("lat"), intent.extras!!.getDouble("long"));
        controller.setCenter(startPoint);
        controller.animateTo(startPoint)
        controller.setZoom(16.5)
        addMarker(binding.osmmap, startPoint)

        Logger.e("MAPVIEW onCreate:in ${controller.zoomIn()}")
        Logger.e("MAPVIEW onCreate: out  ${controller.zoomOut()}")

        // controller.animateTo(mapPoint)
        binding.osmmap.overlays.add(mMyLocationOverlay)

        binding.osmmap.addMapListener(this)


        /*Back press handler*/
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (isTaskRoot) {
                    // If this activity is the root of the task
                    // Navigate to a specific activity, let's say MainActivity
                    val intent = Intent(applicationContext, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    finish()
                }
                // Code that you need to execute on back press, e.g. finish()
            }
        })
    }


    // Map scroll listener

    override fun onScroll(event: ScrollEvent?): Boolean {
        // event?.source?.getMapCenter()
        Logger.e("onCreate:la ${event?.source?.getMapCenter()?.latitude}")
        Logger.e("onCreate:lo ${event?.source?.getMapCenter()?.longitude}")
        //  Log.e("TAG", "onScroll   x: ${event?.x}  y: ${event?.y}", )
        return true
    }

    // Map zoom listener
    override fun onZoom(event: ZoomEvent?): Boolean {
        //  event?.zoomLevel?.let { controller.setZoom(it) }


        Logger.e("onZoom zoom level: ${event?.zoomLevel}   source:  ${event?.source}")
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

        try {
            stopLocationUpdates()
        } catch (ex: Exception) {

        }
    }

    // Stop location updates
    private fun stopLocationUpdates() {
        if (locationCallback != null) {
            if (fusedLocationClient != null) {
                fusedLocationClient.removeLocationUpdates(locationCallback!!)
            }
            locationCallback = null
        }
    }
//    override fun onBackPressed() {
//        if (isTaskRoot) {
//            // If this activity is the root of the task
//            // Navigate to a specific activity, let's say MainActivity
//            val intent = Intent(this, HomeActivity::class.java)
//            startActivity(intent)
//            finish()
//        } else {
//            // If there are more activities in the stack
//            // Finish the current activity
//            onBackPressedDispatcher.onBackPressed() // with this line
//                }
//    }
}