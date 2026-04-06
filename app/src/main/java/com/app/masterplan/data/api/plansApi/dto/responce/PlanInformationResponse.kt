package com.app.masterplan.data.api.plansApi.dto.responce

import java.time.LocalDate
import java.util.UUID

data class PlanInformationResponse(
    val id: UUID,
    val title: String,
    val description: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val status: String,
    val directorId: UUID? = null,
    val documentId: UUID? = null
)
