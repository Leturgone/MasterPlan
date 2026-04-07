package com.app.masterplan.domain.useacse.notifications

import com.app.masterplan.domain.ports.NotificationPort
import java.util.UUID

class SubscribeOnNotificationsUseCase(
    private val notificationPort: NotificationPort
) {

    suspend operator fun invoke(subscriberId: UUID){
        notificationPort.subscribeToUserNotifications(userId =  subscriberId)
    }
}