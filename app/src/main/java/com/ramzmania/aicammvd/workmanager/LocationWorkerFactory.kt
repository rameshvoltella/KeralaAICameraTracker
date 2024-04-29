package com.ramzmania.aicammvd.workmanager
import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.ramzmania.aicammvd.data.local.LocalRepository
import javax.inject.Inject
/**
 * Factory class for creating instances of [LocationWorker].
 *
 * @param localRepository The repository for accessing local data.
 */
class LocationWorkerFactory @Inject constructor(private val localRepository: LocalRepository) : WorkerFactory() {
    /**
     * Creates a [LocationWorker] instance with the provided parameters.
     *
     * @param appContext The application context.
     * @param workerClassName The class name of the worker.
     * @param workerParameters The parameters for the worker.
     * @return A [ListenableWorker] instance.
     */
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker = LocationWorker(appContext, workerParameters, localRepository)
}