package com.ramzmania.aicammvd.data.local

import com.ramzmania.aicammvd.data.Resource
import com.ramzmania.aicammvd.data.dto.cameralist.CameraData
import kotlinx.coroutines.flow.Flow

interface LocalRepositorySource {
    suspend fun requestCameraLocation(): Flow<Resource<CameraData>>

}