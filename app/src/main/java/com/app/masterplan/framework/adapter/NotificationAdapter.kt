package com.app.masterplan.framework.adapter

import android.content.Context
import android.content.Intent
import com.app.masterplan.domain.model.userManagement.UserRole
import com.app.masterplan.domain.ports.NotificationPort
import com.app.masterplan.framework.service.WebsocketNotificationService
import java.util.UUID
import javax.inject.Inject

class NotificationAdapter @Inject constructor(
    private val context: Context
): NotificationPort {


    override suspend fun subscribeToUserNotifications(userRole: UserRole,userId: UUID) {
        val intent = Intent(context, WebsocketNotificationService::class.java).apply {
            action = WebsocketNotificationService.ACTION_SUBSCRIBE
            putExtra("USER_ID",userId)
            putExtra("USER_ROLE",userRole)
        }
        context.startService(intent)


    }

    override suspend fun unsubscribeFromNotifications() {
        val intent = Intent(context, WebsocketNotificationService::class.java).apply {
            action = WebsocketNotificationService.ACTION_UNSUBSCRIBE
        }
        context.startService(intent)
    }

}