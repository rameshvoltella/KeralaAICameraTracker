package com.ramzmania.aicammvd.ui.screens.mapview


import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import com.ramzmania.aicammvd.R
import com.ramzmania.aicammvd.databinding.MapViewBinding
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


class OsmMaptActivity : ComponentActivity(), MapListener{





    lateinit var mMap: MapView
    lateinit var controller: IMapController;
    lateinit var mMyLocationOverlay: MyLocationNewOverlay;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = MapViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Configuration.getInstance().load(
            applicationContext,
            getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE)
        )
        mMap = binding.osmmap
        mMap.setTileSource(TileSourceFactory.MAPNIK)
        mMap.mapCenter
        mMap.setMultiTouchControls(true)
        mMap.getLocalVisibleRect(Rect())


        mMyLocationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(this), mMap)
        controller = mMap.controller


//        mMyLocationOverlay.enableMyLocation()
//        mMyLocationOverlay.enableFollowLocation()
        mMyLocationOverlay.isDrawAccuracyEnabled = true
        mMyLocationOverlay.setPersonAnchor(1f,1f)
        Log.d("kkppp","yesss")

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
        Log.d("kkppp","yesss")
        val startPoint = GeoPoint(intent.extras!!.getDouble("lat"), intent.extras!!.getDouble("long"));
        controller.setCenter(startPoint);
        controller.animateTo(startPoint)
        controller.setZoom(16.5)
        addMarker(mMap,startPoint)
//        val startMarker = Marker(mMap)
//        startMarker.position = startPoint
//        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
//        mMap.getOverlays().add(startMarker)

        Log.e("TAG", "onCreate:in ${controller.zoomIn()}")
        Log.e("TAG", "onCreate: out  ${controller.zoomOut()}")

        // controller.animateTo(mapPoint)
        mMap.overlays.add(mMyLocationOverlay)

        mMap.addMapListener(this)


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
        val icon = context.resources.getDrawable(R.drawable.ai_camera_marker, context.theme) // Load the custom marker drawable

        val marker = Marker(mapView)
        marker.position = point
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        marker.icon = icon // Set the custom icon
        marker.title = "Custom Marker at $point" // Optional: you can set title for marker

        mapView.overlays.add(marker)
        mapView.invalidate() // Refresh the map to display the marker
    }
}