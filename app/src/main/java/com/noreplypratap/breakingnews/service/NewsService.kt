package com.noreplypratap.breakingnews.service

import android.annotation.SuppressLint
import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.noreplypratap.breakingnews.utils.logMessage

class NewsService: Service() {

    private val TAG = "NewsServiceTag"
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }


    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    override fun onCreate() {
        super.onCreate()
        logMessage(TAG,"onCreate")
    }
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        logMessage(TAG,"onDestroy")
    }
}