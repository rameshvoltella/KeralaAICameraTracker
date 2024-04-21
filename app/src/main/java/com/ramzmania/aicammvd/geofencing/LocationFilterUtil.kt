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