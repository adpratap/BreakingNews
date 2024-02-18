package com.noreplypratap.breakingnews.core.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.noreplypratap.domain.usecases.remote.GetArticlesUseCase
import javax.inject.Inject

class NewsWorkerFactory @Inject constructor(private val getArticlesUseCase: GetArticlesUseCase) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? = NewsWorker(appContext,workerParameters,getArticlesUseCase)
}