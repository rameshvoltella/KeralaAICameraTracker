package com.ramzmania.aicammvd.data.local

import com.ramzmania.aicammvd.data.Resource
import com.ramzmania.aicammvd.data.dto.cameralist.CameraData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class LocalRepository @Inject constructor(
    private val localRepository: LocalData,
    private val ioDispatcher: CoroutineContext
):LocalRepositorySource {
    override suspend fun requestCameraLocation(): Flow<Resource<CameraData>> {
        return flow{emit(localRepository.requestCameraLocation())}
    }

}