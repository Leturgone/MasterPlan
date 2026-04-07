package com.app.masterplan.domain.ports

import java.util.UUID

interface NotificationPort {

    suspend fun subscribeToUserNotifications(userId: UUID)

    suspend fun unsubscribeFromNotifications()

}