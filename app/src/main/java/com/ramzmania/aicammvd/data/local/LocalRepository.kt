package com.ramzmania.aicammvd.data.local

import com.ramzmania.aicammvd.data.Resource
import com.ramzmania.aicammvd.data.dto.cameralist.CameraData
import com.ramzmania.aicammvd.data.dto.cameralist.CameraDataResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class LocalRepository @Inject constructor(
    private val localRepository: LocalData,
    private val ioDispatcher: CoroutineContext
):LocalRepositorySource {
    override suspend fun requestCameraLocation(): Flow<Resource<CameraDataResponse>> {
        return flow{emit(localRepository.requestCameraLocation())}
    }

    override suspend fun setNewAiCameraCircle(cameraList: List<CameraData>,currentLat: Double, currentLong: Double): Flow<Resource<Boolean>> {
        return flow { emit(localRepository.setNewAiCameraCircleData(cameraList,currentLat,currentLong)) }
    }


}