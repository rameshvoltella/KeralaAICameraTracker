package com.ramzmania.aicammvd.data.local

import com.ramzmania.aicammvd.data.Resource
import com.ramzmania.aicammvd.data.dto.cameralist.CameraData
import com.ramzmania.aicammvd.data.dto.cameralist.CameraDataResponse

interface LocalDataSource {
    suspend fun requestCameraLocation():Resource<CameraDataResponse>
}