package com.ramzmania.aicammvd.data.local

import com.ramzmania.aicammvd.data.ContextModule
import com.ramzmania.aicammvd.data.Resource
import com.ramzmania.aicammvd.data.dto.cameralist.CameraData
import com.ramzmania.aicammvd.data.dto.cameralist.CameraDataResponse
import com.ramzmania.aicammvd.geofencing.createGeofenceList
import com.ramzmania.aicammvd.geofencing.findNearestCameras
import com.ramzmania.aicammvd.geofencing.getAllLocationList
import com.ramzmania.aicammvd.geofencing.removeAllGeofences
import com.ramzmania.aicammvd.geofencing.setBatchGeoFencing

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * This package contains classes responsible for handling local data operations.
 * It includes classes for accessing and manipulating camera location data locally.
 */
class LocalData @Inject
constructor(
private val contextModule: ContextModule
) :LocalDataSource{
    /**
     * Retrieves camera location data from the local data source.
     *
     * @return A [Resource] containing the camera location data.
     */
    override suspend fun requestCameraLocation(): Resource<CameraDataResponse> {
        var cameraData:List<CameraData>?=null
        withContext(Dispatchers.IO)
        {
             cameraData = getAllLocationList(contextModule.context)


        }

        return if (cameraData != null) {
           // Thread.sleep(3000)

            Resource.Success(CameraDataResponse(cameraData!!))
        } else {
            Resource.DataError(401)
        }

    }

    /**
     * Sets new AI camera circle data based on the current location.
     *
     * @param currentLat The current latitude.
     * @param currentLong The current longitude.
     * @return A [Resource] indicating the success of the operation.
     */
    override suspend fun setNewAiCameraCircleData(currentLat: Double, currentLong: Double): Resource<Boolean> {

        var operationSuccess=false
        withContext(Dispatchers.IO)
        {
            try {

                removeAllGeofences(contextModule.context, false,null)

//        val adapter = moshi.adapter(CameraData::class.java)
                val fullCameraList = getAllLocationList(contextModule.context)


                if (fullCameraList != null) {

                    val nearestCameraList= fullCameraList?.findNearestCameras(
                        currentLat,
                        currentLong,true
                    )

                    if(nearestCameraList!=null) {

                        val updatedCameraList = createGeofenceList(nearestCameraList!!)
                        setBatchGeoFencing(contextModule.context, updatedCameraList)
                        operationSuccess = true
                    }else
                    {

                        operationSuccess = false

                    }
                }
            }catch (ex:Exception)
            {

                operationSuccess=false
            }

        }
        return Resource.Success(operationSuccess)
    }


}