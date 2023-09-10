package com.noreplypratap.breakingnews

import android.app.Application
import android.util.Log
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.noreplypratap.breakingnews.newsworker.NewsWorker
import com.noreplypratap.breakingnews.newsworker.NewsWorkerFactory
import com.noreplypratap.breakingnews.utils.logging
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class BreakingNewsApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var newsWorkerFactory: NewsWorkerFactory
    override fun onCreate() {
        super.onCreate()
        setupNewsWorker()
    }

    private fun setupNewsWorker() {
        logging("setupWorker... Application")
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val workRequest =
            PeriodicWorkRequest.Builder(NewsWorker::class.java, 15, TimeUnit.MINUTES)
                .setConstraints(constraints).build()
        WorkManager.getInstance(applicationContext).enqueue(workRequest)
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder().setMinimumLoggingLevel(Log.DEBUG)
            .setWorkerFactory(newsWorkerFactory).build()
    }

}