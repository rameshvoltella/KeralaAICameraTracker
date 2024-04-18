package com.ramzmania.aicammvd.geofencing

import android.content.Context
import android.location.Location
import com.ramzmania.aicammvd.data.local.LocalRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingGeoFence {

    private val serviceScope = CoroutineScope(Dispatchers.IO) // Runs tasks in the background

    fun setNewGeoFence(localRepository: LocalRepository,location:Location,context: Context)
    {
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

                        setBatchGeoFencing(context, updatedCameraList)
                    }
//                Log.d("kona","collected"+nearestCameraList.size)
                }
            }
        }
    }

}