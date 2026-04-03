package com.app.masterplan.data.api.plansApi.dto.request

import java.time.LocalDate
import java.util.UUID

data class UpdateTaskRequest (
    val id: UUID,
    val title: String,
    val description: String,
    val urgency: Double,
    val endDate: LocalDate,
    val status: String,
    val planId: UUID,
    val documentId: UUID? = null,
    val executorsIds: List<UUID>
)