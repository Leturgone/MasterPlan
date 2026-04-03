package com.app.masterplan.data.api.plansApi.dto.request

import java.time.LocalDate
import java.util.UUID

data class CreateTaskRequest(
    val planId: UUID,
    val taskId: UUID? = null,
    val title: String,
    val description: String,
    val endDate: LocalDate,
    val executorsIds: List<UUID>
)