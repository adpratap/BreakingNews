package com.noreplypratap.breakingnews

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.noreplypratap.breakingnews.core.worker.NewsWorker
import com.noreplypratap.breakingnews.core.worker.NewsWorkerFactory
import com.noreplypratap.breakingnews.core.utils.logMessage
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit
import javax.inject.Inject


const val TAG = "BreakingNewsApplicationTag"
private val PERMISSION_REQUEST_CODE = 1
@HiltAndroidApp
class BreakingNewsApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var newsWorkerFactory: NewsWorkerFactory
    override fun onCreate() {
        super.onCreate()
        setupNewsWorker()
    }

    private fun setupNewsWorker() {
        logMessage(TAG,"Setup News Worker...")
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val workRequest =
            PeriodicWorkRequest.Builder(NewsWorker::class.java, 1, TimeUnit.HOURS)
                .setConstraints(constraints).build()
        WorkManager.getInstance(applicationContext).enqueue(workRequest)
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder().setMinimumLoggingLevel(Log.DEBUG)
            .setWorkerFactory(newsWorkerFactory).build()
    }

    fun requestPermissions(activity: AppCompatActivity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val permissionsToRequest = mutableListOf<String>()

            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.FOREGROUND_SERVICE) != PackageManager.PERMISSION_GRANTED) {
                permissionsToRequest.add(Manifest.permission.FOREGROUND_SERVICE)
            }

            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                permissionsToRequest.add(Manifest.permission.POST_NOTIFICATIONS)
            }

            if (permissionsToRequest.isNotEmpty()) {
                ActivityCompat.requestPermissions(activity, permissionsToRequest.toTypedArray(), PERMISSION_REQUEST_CODE)
            }
        }
    }

}