package com.app.masterplan.domain.model.notification

import java.time.LocalDateTime

data class PushNotification(
    val notificationId: Int,
    val notificationType: NotificationType,
    val title: String,
    val message: String,
    val timestamp: LocalDateTime
)
