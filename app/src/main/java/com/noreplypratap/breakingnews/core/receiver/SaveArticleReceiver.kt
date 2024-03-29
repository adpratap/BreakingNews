package com.noreplypratap.breakingnews.core.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.noreplypratap.breakingnews.core.utils.logMessage

const val TAG = "SaveArticleReceiverTag"
class SaveArticleReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.action?.let {
            val receivedValue = intent.getStringExtra("id")
            logMessage(TAG,"$receivedValue")
            //context?.sendIntent(it)
        }
    }
}
