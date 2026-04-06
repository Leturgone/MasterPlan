package com.app.masterplan.data.api.plansApi.dto.request

import java.time.LocalDate
import java.util.UUID

data class CreatePlanRequest(
    val id: UUID? = null,
    val title: String,
    val description: String,
    val startDate: LocalDate? = null,
    val endDate: LocalDate,
    val directorId: UUID
)
