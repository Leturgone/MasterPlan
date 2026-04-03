package com.app.masterplan.domain.model.adminRequests

import java.time.LocalDateTime
import java.util.UUID

data class AdminRequest(
    val id: UUID,
    val title: String,
    val description: String,
    val creationDate: LocalDateTime,
    val senderId: UUID,
    val status: AdminRequestStatus
)
