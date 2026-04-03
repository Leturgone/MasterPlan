package com.app.masterplan.data.api.plansApi.dto.responce

import java.time.LocalDate
import java.util.UUID

data class TaskInformationResponse(
    val id: UUID,
    val title: String,
    val description: String,
    val endDate: LocalDate,
    val status: String,
    val planId: UUID,
    val documentId: UUID? = null,
    val urgency: Double,
    val executorsIds: List<UUID>
)
