package com.noreplypratap.breakingnews.notification

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.noreplypratap.breakingnews.R
import com.noreplypratap.breakingnews.utils.Constants.CHANNEL_ID

@SuppressLint("ObsoleteSdkInt")
fun Context.buildNotification(
    icon: Int,
    title: String,
    desc: String,
    intent: Intent
): Notification {

    val bigTextStyle = Notification.BigTextStyle()
    bigTextStyle.setBigContentTitle(title)
    bigTextStyle.bigText(desc)

    var requestCode = 0
    val pendingIntent = PendingIntent.getActivity(
        applicationContext,
        requestCode++, // Use a unique request code if you have multiple notifications
        intent,
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.FLAG_IMMUTABLE
        } else 0
    )

    //for BroadCastReceiver
    //val pendingIntent = PendingIntent.getBroadcast(applicationContext, requestCode++, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

	val builder = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
        val b = Notification.Builder(this)
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
            b.setPriority(Notification.PRIORITY_MIN)
        }
        b
    } else {
        createChannel()
        Notification.Builder(this, CHANNEL_ID)
    }.setOngoing(true)
        .setOnlyAlertOnce(true)
        .setSmallIcon(icon)
        .setContentTitle(title)
        .setStyle(bigTextStyle)
        .setContentIntent(pendingIntent)
    return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
        builder.notification
    } else {
        builder.build()
    }
}

fun Context.createChannel() {
    val nm = getSystemService(
        Context.NOTIFICATION_SERVICE
    ) as NotificationManager
    if (nm.getNotificationChannel(CHANNEL_ID) == null) {
        val channel = NotificationChannel(
            CHANNEL_ID,
            "Breaking News",
            NotificationManager.IMPORTANCE_LOW
        )
        channel.description = getString(R.string.notification_desc)
        channel.lockscreenVisibility = Notification.VISIBILITY_SECRET
        channel.setSound(null, null)
        channel.setShowBadge(false)
        channel.enableLights(false)
        channel.enableVibration(true)
        nm.createNotificationChannel(channel)
    }
}
