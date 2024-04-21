package com.ramzmania.aicammvd.data.local

import com.ramzmania.aicammvd.data.Resource
import com.ramzmania.aicammvd.data.dto.cameralist.CameraData
import com.ramzmania.aicammvd.data.dto.cameralist.CameraDataResponse
import kotlinx.coroutines.flow.Flow

interface LocalRepositorySource {
    suspend fun requestCameraLocation(): Flow<Resource<CameraDataResponse>>
    suspend fun setNewAiCameraCircle(cameraList:List<CameraData>,currentLat: Double, currentLong: Double):Flow<Resource<Boolean>>

}