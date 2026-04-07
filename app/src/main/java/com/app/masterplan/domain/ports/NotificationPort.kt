package com.app.masterplan.domain.ports

import com.app.masterplan.domain.model.userManagement.UserRole
import java.util.UUID

interface NotificationPort {

    suspend fun subscribeToUserNotifications(userRole: UserRole,userId: UUID)

    suspend fun unsubscribeFromNotifications()

}