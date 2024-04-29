package com.ramzmania.aicammvd.geofencing

import android.content.Context
import com.google.android.gms.location.Geofence
import com.ramzmania.aicammvd.data.dto.cameralist.CameraData
import com.ramzmania.aicammvd.data.moshiFactories.AiCameraStandardJsonAdapters
import com.ramzmania.aicammvd.data.moshiFactories.MyKotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.*
/**
 * Retrieves the list of camera locations from a JSON file stored in the assets directory.
 *
 * @param context The application context.
 * @return The list of camera locations, or null if an error occurs.
 */
suspend fun getAllLocationList(context:Context): List<CameraData>? {
    val jsonFileName = "ailocations.json"
    var nearestCameraList:List<CameraData>?=null
    withContext(Dispatchers.IO)
    {
        try {
            val json =
                context.assets.open(jsonFileName).bufferedReader()
                    .use { it.readText() }

//        Thread.sleep(8000)


            val moshi = Moshi.Builder()
                .add(MyKotlinJsonAdapterFactory())
                .add(AiCameraStandardJsonAdapters.FACTORY)
                .build()
            val listType = Types.newParameterizedType(List::class.java, CameraData::class.java)
            val adapter = moshi.adapter<List<CameraData>>(listType)
//        val adapter = moshi.adapter(CameraData::class.java)
             nearestCameraList = adapter.fromJson(json)

        }catch (ex:Exception)
        {
        }

    }
    return nearestCameraList

}

/**
 * Calculates the distance between two geographical coordinates using the Haversine formula.
 *
 * @param lat1 The latitude of the first coordinate.
 * @param lon1 The longitude of the first coordinate.
 * @param lat2 The latitude of the second coordinate.
 * @param lon2 The longitude of the second coordinate.
 * @return The distance between the coordinates in kilometers.
 */
fun calculateDistance(
    lat1: Double,
    lon1: Double,
    lat2: Double,
    lon2: Double
): Double {
    val R = 6371 // Radius of the earth in km
    val dLat = deg2rad(lat2 - lat1)  // deg2rad below
    val dLon = deg2rad(lon2 - lon1)
    val a =
        sin(dLat / 2) * sin(dLat / 2) +
                cos(deg2rad(lat1)) * cos(deg2rad(lat2)) *
                sin(dLon / 2) * sin(dLon / 2)
    val c = 2 * atan2(sqrt(a), sqrt(1 - a))
    val distance = R * c // Distance in km
    return distance
}

/**
 * Converts degrees to radians.
 *
 * @param deg The angle in degrees.
 * @return The angle converted to radians.
 */
fun deg2rad(deg: Double): Double {
    return deg * (Math.PI / 180)
}