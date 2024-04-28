package com.ramzmania.aicammvd.ui.screens.mapview


import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Rect
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.widget.TextView
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
import com.ramzmania.aicammvd.utils.Constants
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


class OsmMapActivity : ComponentActivity(), MapListener {


    lateinit var mMap: MapView
    lateinit var speedTextView: TextView

    lateinit var controller: IMapController;
    lateinit var mMyLocationOverlay: MyLocationNewOverlay;

    private val locationManager: LocationManager? = null
    private val locationListener: LocationListener? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = MapViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Configuration.getInstance().load(
            applicationContext,
            getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE)
        )
        mMap = binding.osmmap
        speedTextView=binding.speedTxt
        mMap.setTileSource(TileSourceFactory.MAPNIK)
        mMap.mapCenter
        mMap.setMultiTouchControls(true)
        mMap.getLocalVisibleRect(Rect())

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mMyLocationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(this), mMap)
        controller = mMap.controller


//        mMyLocationOverlay.enableMyLocation()
//        mMyLocationOverlay.enableFollowLocation()
        mMyLocationOverlay.isDrawAccuracyEnabled = true
        mMyLocationOverlay.setPersonAnchor(1f, 1f)

//        mMyLocationOverlay.runOnFirstFix {
//            runOnUiThread {
////                controller.setCenter(mMyLocationOverlay.myLocation);
////                controller.animateTo(mMyLocationOverlay.myLocation)
//                Log.d("kkppp","yesss")
//                val startPoint = GeoPoint(intent.extras!!.getDouble("lat"), intent.extras!!.getDouble("long"));
//                controller.setCenter(startPoint);
//               controller.animateTo(startPoint)
//                Log.d("kkppp","yesss22222"+intent.extras!!.getDouble("lat"))
//
//
//            }
//        }
        // val mapPoint = GeoPoint(latitude, longitude)

//        controller.setZoom(6.0)
        val startPoint =
            GeoPoint(intent.extras!!.getDouble("lat"), intent.extras!!.getDouble("long"));
        controller.setCenter(startPoint);
        controller.animateTo(startPoint)
        controller.setZoom(16.5)
        addMarker(mMap, startPoint)
//        val startMarker = Marker(mMap)
//        startMarker.position = startPoint
//        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
//        mMap.getOverlays().add(startMarker)

        Log.e("TAG", "onCreate:in ${controller.zoomIn()}")
        Log.e("TAG", "onCreate: out  ${controller.zoomOut()}")

        // controller.animateTo(mapPoint)
        mMap.overlays.add(mMyLocationOverlay)

        mMap.addMapListener(this)
        if (intent.extras!!.containsKey(Constants.INTENT_FROM_GEO)) {
            // Enable location and follow location
            mMyLocationOverlay.enableMyLocation()
            mMyLocationOverlay.enableFollowLocation()
        }
    }

    override fun onScroll(event: ScrollEvent?): Boolean {
        // event?.source?.getMapCenter()
        Log.e("TAG", "onCreate:la ${event?.source?.getMapCenter()?.latitude}")
        Log.e("TAG", "onCreate:lo ${event?.source?.getMapCenter()?.longitude}")
        //  Log.e("TAG", "onScroll   x: ${event?.x}  y: ${event?.y}", )
        return true
    }

    override fun onZoom(event: ZoomEvent?): Boolean {
        //  event?.zoomLevel?.let { controller.setZoom(it) }


        Log.e("TAG", "onZoom zoom level: ${event?.zoomLevel}   source:  ${event?.source}")
        return false;
    }


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

    private fun startLocationUpdates() {
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000)
            .setWaitForAccurateLocation(true)
            .setMinUpdateIntervalMillis(1000)
            .setMaxUpdateDelayMillis(1000)
            .build()


        val locationCallback = object : LocationCallback() {
            @SuppressLint("SetTextI18n")
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult ?: return
                for (location in locationResult.locations) {
                    // Calculate speed here using the Location object


                    val speed = location.speed // Speed in meters/second
//                    val speedKmH = speed * 3.6 // Convert speed to km/h
                    // Now you have the speed, you can use it as needed
                    val speedKmH = String.format("%.1f", speed * 3.6)
                    speedTextView.text = "Speed\n $speedKmH Km/H"
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
            locationCallback,
            null /* Looper */
        )
    }
}