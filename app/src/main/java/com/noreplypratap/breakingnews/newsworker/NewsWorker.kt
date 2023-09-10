package com.noreplypratap.breakingnews.newsworker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.noreplypratap.breakingnews.network.isOnline
import com.noreplypratap.breakingnews.repository.Repository
import com.noreplypratap.breakingnews.utils.codeIndia
import com.noreplypratap.breakingnews.utils.logging
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltWorker
class NewsWorker @AssistedInject constructor (
    @Assisted private val context: Context,
    @Assisted params: WorkerParameters,
    @Assisted private val repository: Repository
) : Worker(context, params) {
    override fun doWork(): Result {
        CoroutineScope(Dispatchers.IO).launch {
            logging("Worker Called ... ")
            if (context.isOnline()){
                val newsList = repository.getNewsData(codeIndia,"","")
                //val oldNewsList = repository.getArticleRoomDB()
                logging("...............................................$newsList")
            }else {
                logging("No Internet ... ")
            }
        }
        return Result.success()
    }
}