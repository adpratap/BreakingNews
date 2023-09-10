package com.noreplypratap.breakingnews.newsworker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.noreplypratap.breakingnews.repository.Repository
import javax.inject.Inject

class NewsWorkerFactory @Inject constructor(private val repositoryImpl: Repository) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? = NewsWorker(appContext,workerParameters,repositoryImpl)
}