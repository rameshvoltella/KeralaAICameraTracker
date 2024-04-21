package com.ramzmania.aicammvd.workmanager
import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.ramzmania.aicammvd.data.local.LocalRepository
import javax.inject.Inject
class LocationWorkerFactory @Inject constructor(private val localRepository: LocalRepository) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker = LocationWorker(appContext, workerParameters, localRepository)
}