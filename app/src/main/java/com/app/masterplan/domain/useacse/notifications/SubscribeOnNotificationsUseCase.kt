package com.app.masterplan.domain.useacse.notifications

import com.app.masterplan.domain.model.userManagement.UserRole
import com.app.masterplan.domain.ports.NotificationPort
import java.util.UUID

class SubscribeOnNotificationsUseCase(
    private val notificationPort: NotificationPort
) {

    suspend operator fun invoke(subscriberId: UUID,subscriberRole: UserRole){
        notificationPort.subscribeToUserNotifications(
            userId =  subscriberId,
            userRole = subscriberRole
        )
    }
}