package com.app.masterplan.domain.useacse.notifications

import com.app.masterplan.domain.ports.NotificationPort

class UnsubscribeOnNotificationsUseCase(
    private val notificationPort: NotificationPort
) {
    suspend operator fun invoke(){
        notificationPort.unsubscribeFromNotifications()
    }
}