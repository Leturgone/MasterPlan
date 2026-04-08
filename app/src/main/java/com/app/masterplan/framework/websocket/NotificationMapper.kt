package com.app.masterplan.framework.websocket

import com.app.masterplan.domain.model.notification.NotificationType
import com.app.masterplan.domain.model.notification.PushNotification
import com.google.gson.Gson
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

internal object NotificationMapper {
    fun toDomain(stompText: String): PushNotification{
        val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
        val bodyStart = stompText.indexOf("\n\n") + 2
        val bodyEnd = stompText.indexOfLast { it == '\u0000' }
        val jsonBody = stompText.substring(bodyStart, bodyEnd)
        val dto = Gson().fromJson(jsonBody, NotificationDTO::class.java)

        return PushNotification(
            notificationId = dto.notificationId,
            notificationType = NotificationType.valueOf(dto.notificationType),
            title = dto.title,
            message = dto.message,
            timestamp = LocalDateTime.parse(dto.timestamp,formatter)
        )
    }
}