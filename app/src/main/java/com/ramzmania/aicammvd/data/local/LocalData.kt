package com.ramzmania.aicammvd.data.local

import android.util.Log
import com.ramzmania.aicammvd.data.ContextModule
import com.ramzmania.aicammvd.data.Resource
import com.ramzmania.aicammvd.data.dto.cameralist.CameraData
import com.ramzmania.aicammvd.data.dto.cameralist.CameraDataResponse
import com.ramzmania.aicammvd.data.moshiFactories.MyKotlinJsonAdapterFactory
import com.ramzmania.aicammvd.data.moshiFactories.AiCameraStandardJsonAdapters
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalData @Inject
constructor(
private val contextModule: ContextModule
) :LocalDataSource{
    override suspend fun requestCameraLocation(): Resource<CameraDataResponse> {
        val jsonFileName = "ailocations.json" // Replace with the name of your JSON file

//        val json = contextModule.context.assets.open(jsonFileName).bufferedReader().use {
//            it.readText()
//        }
        val json = withContext(Dispatchers.IO) {
//            Thread.sleep(8000)
            Log.d("Stared","yoooo")
            contextModule.context.assets.open(jsonFileName).bufferedReader().use { it.readText() }
        }
//        Thread.sleep(8000)

        Log.d("Stared","yoooo2222")

        val moshi = Moshi.Builder()
            .add(MyKotlinJsonAdapterFactory())
            .add(AiCameraStandardJsonAdapters.FACTORY)
            .build()
        val listType = Types.newParameterizedType(List::class.java, CameraData::class.java)
        val adapter = moshi.adapter<List<CameraData>>(listType)
//        val adapter = moshi.adapter(CameraData::class.java)
        val cameraData = adapter.fromJson(json)

        return if (cameraData != null) {
           // Thread.sleep(3000)

            Resource.Success(CameraDataResponse(cameraData))
        } else {
            Resource.DataError(401)
        }

    }
}