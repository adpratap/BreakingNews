package com.noreplypratap.breakingnews.core.worker

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.noreplypratap.breakingnews.R
import com.noreplypratap.breakingnews.core.notification.buildNotification
import com.noreplypratap.breakingnews.presentation.views.NewsActivity
import com.noreplypratap.breakingnews.core.utils.codeIndia
import com.noreplypratap.breakingnews.core.utils.isOnline
import com.noreplypratap.breakingnews.core.utils.logMessage
import com.noreplypratap.domain.model.NewsArticle
import com.noreplypratap.domain.usecases.remote.GetArticlesUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

const val TAG = "NewsWorkerTAG"
@HiltWorker
class NewsWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted params: WorkerParameters,
    @Assisted private val getArticlesUseCase: GetArticlesUseCase
) : Worker(context, params) {
    var counter: Int = 0
    private lateinit var notificationManager: NotificationManager
    override fun doWork(): Result {
        notificationManager = applicationContext.getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as NotificationManager
        logMessage(TAG, "Worker Started .... ")
        counter = 0
        CoroutineScope(Dispatchers.Main).launch {
            if (context.isOnline()) {
                getArticlesUseCase(codeIndia).collect { articles ->
                    articles.data?.forEach { article ->
                        if (counter < 5) {
                            logMessage(TAG, "Worker Started .... $counter ")
                            updateNotification(counter++,article)
                        }
                    }

                }
            }
        }
        return Result.success()
    }

    override fun onStopped() {
        super.onStopped()
        logMessage(TAG, "onStopped .... ")
    }
    private fun Context.buildAndScheduleNotification(article: NewsArticle, intent: Intent): Notification {
        return buildNotification(
            R.drawable.launcher_new,
            article.title.toString(),
            article.description.toString(),
            intent
        )
    }

    private fun updateNotification(id: Int, article: NewsArticle) {
        val intent = Intent(applicationContext, NewsActivity::class.java).apply {
            action = "com.noreplypratap.breakingnews.SAVE_ARTICLE_ACTION"
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("id", article.title.toString())
        }

//        val intent = Intent(applicationContext, SaveArticleReceiver::class.java).apply {
//            action = "com.noreplypratap.breakingnews.SAVE_ARTICLE_ACTION"
//            putExtra("id", article.title.toString())
//        }


        val notification = applicationContext.buildAndScheduleNotification(article,intent)
        notificationManager.notify(id, notification)
    }

}