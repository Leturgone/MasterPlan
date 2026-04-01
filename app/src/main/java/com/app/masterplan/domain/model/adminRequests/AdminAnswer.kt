package com.app.masterplan.domain.model.adminRequests

import java.util.UUID

data class AdminAnswer(
    val id: UUID,
    val title: String,
    val description: String,
    val adminRequestId: UUID
)
