package com.app.masterplan.domain.model.plans

import java.time.LocalDate
import java.util.UUID

data class Plan(
    val id: UUID,
    val title: String,
    val description: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val status: PlanStatus,
    val directorId: UUID? = null,
    val documentId: UUID? = null
)
