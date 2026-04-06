package com.app.masterplan.domain.model.plans

import java.time.LocalDate
import java.util.UUID

data class Task(
    val id: UUID,
    val title: String,
    val description: String,
    val urgency: Double,
    val endDate: LocalDate,
    val status: TaskStatus,
    val planId: UUID,
    val documentId: UUID? = null,
    val executorsIds: List<UUID>
)
