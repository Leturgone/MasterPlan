package com.app.masterplan.data.api.plansApi.dto.request

import java.time.LocalDate
import java.util.UUID

data class UpdatePlanRequest(
    val id: UUID,
    val title: String,
    val description: String,
    val startDate: LocalDate? = null,
    val endDate: LocalDate,
    val directorId: UUID,
    val documentId: UUID? = null,
    val status: String
)
