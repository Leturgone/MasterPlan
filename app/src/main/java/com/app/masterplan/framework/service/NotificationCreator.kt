package com.app.masterplan.framework.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.app.masterplan.R
import com.app.masterplan.domain.model.notification.PushNotification
import javax.inject.Inject

class NotificationCreator @Inject constructor(
    private val context: Context,
) {

    private var notificationManager: NotificationManager = context.getSystemService(
        Context.NOTIFICATION_SERVICE) as NotificationManager

    private val channelId = "masterplan_notifications"

    init {
        createNotificationChannel()

    }

    private fun createNotificationChannel(){
        val channel = NotificationChannel(
            channelId,"Уведомления", NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)
    }

    fun showNotification(notification: PushNotification){
        val builder = NotificationCompat.Builder(context,channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(notification.title)
            .setContentText(notification.message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        notificationManager.notify(notification.notificationId, builder.build())
    }

    fun createEmptyNotification(): Notification {
        return NotificationCompat.Builder(context,channelId)
            .setContentTitle("")
            .setContentText("")
            .setAutoCancel(true)
            .build();
    }
}