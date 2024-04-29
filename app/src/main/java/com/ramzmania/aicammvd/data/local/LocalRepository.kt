package com.ramzmania.aicammvd.data.local

import com.ramzmania.aicammvd.data.Resource
import com.ramzmania.aicammvd.data.dto.cameralist.CameraDataResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
/**
 * Repository class for handling local data operations.
 * Acts as an intermediary between the data source and the rest of the application.
 *
 * @param localRepository The local data source providing access to camera location data.
 * @param ioDispatcher The coroutine context for running IO-bound operations.
 */
class LocalRepository @Inject constructor(
    private val localRepository: LocalData,
    private val ioDispatcher: CoroutineContext
):LocalRepositorySource {

    /**
     * Retrieves camera location data from the local data source.
     *
     * @return A flow emitting a [Resource] containing the camera location data.
     */
    override suspend fun requestCameraLocation(): Flow<Resource<CameraDataResponse>> {
        return flow{emit(localRepository.requestCameraLocation())}
    }
    /**
     * Sets new AI camera circle data based on the current location.
     *
     * @param currentLat The current latitude.
     * @param currentLong The current longitude.
     * @return A flow emitting a [Resource] indicating the success of the operation.
     */
    override suspend fun setNewAiCameraCircle(currentLat: Double, currentLong: Double): Flow<Resource<Boolean>> {

        return flow {
            emit(localRepository.setNewAiCameraCircleData(currentLat,currentLong))
        }
    }


}