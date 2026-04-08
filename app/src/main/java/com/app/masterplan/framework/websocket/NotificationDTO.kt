package com.app.masterplan.framework.websocket

@kotlinx.serialization.Serializable
class NotificationDTO(
    val notificationId: Int,
    val notificationType: String,
    val title: String,
    val message: String,
    val timestamp: String
)