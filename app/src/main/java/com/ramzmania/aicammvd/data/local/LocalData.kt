package com.ramzmania.aicammvd.data.local

import android.util.Log
import com.ramzmania.aicammvd.data.ContextModule
import com.ramzmania.aicammvd.data.Resource
import com.ramzmania.aicammvd.data.dto.cameralist.CameraData
import com.ramzmania.aicammvd.data.dto.cameralist.CameraDataResponse
import com.ramzmania.aicammvd.data.moshiFactories.MyKotlinJsonAdapterFactory
import com.ramzmania.aicammvd.data.moshiFactories.AiCameraStandardJsonAdapters
import com.ramzmania.aicammvd.geofencing.createGeofenceList
import com.ramzmania.aicammvd.geofencing.findNearestCameras
import com.ramzmania.aicammvd.geofencing.getAllLocationList
import com.ramzmania.aicammvd.geofencing.setBatchGeoFencing
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class LocalData @Inject
constructor(
private val contextModule: ContextModule
) :LocalDataSource{
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

    override suspend fun setNewAiCameraCircleData(cameraList:List<CameraData>,currentLat: Double, currentLong: Double): Resource<Boolean> {
        var operationSuccess=false
        withContext(Dispatchers.IO)
        {
            try {

//        val adapter = moshi.adapter(CameraData::class.java)
                val fullCameraList = getAllLocationList(contextModule.context)

                if (fullCameraList != null) {
                    val nearestCameraList= fullCameraList?.findNearestCameras(
                        currentLat,
                        currentLong
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