package com.app.masterplan.framework.adapter

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.app.masterplan.R
import com.app.masterplan.domain.model.notification.PushNotification
import com.app.masterplan.domain.ports.NotificationPort
import com.app.masterplan.framework.websocket.WebSocketWorker
import java.util.UUID
import javax.inject.Inject

class NotificationAdapter @Inject constructor(
    private val context: Context,
    private val webSocketWorker: WebSocketWorker
): NotificationPort {

    private var notificationManager: NotificationManager = context.getSystemService(
        Context.NOTIFICATION_SERVICE) as NotificationManager

    private val channelId = "masterplan_notifications"

    init {
        createNotificationChannel()

    }

    override suspend fun subscribeToUserNotifications(userId: UUID) {
        webSocketWorker.connectForUser(userId) { notificationData ->
            showNotification(notificationData)

        }
    }

    override suspend fun unsubscribeFromNotifications() {
        webSocketWorker.disconnect()
    }


    private fun createNotificationChannel(){
        val channel = NotificationChannel(
            channelId,"Уведомления", NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)
    }



    private fun showNotification(notification: PushNotification){
        val builder = NotificationCompat.Builder(context,channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(notification.title)
            .setContentText(notification.message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        notificationManager.notify(notification.notificationId, builder.build())
    }
}