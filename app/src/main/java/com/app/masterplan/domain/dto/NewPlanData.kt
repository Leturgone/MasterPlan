package com.app.masterplan.domain.dto

import java.time.LocalDate
import java.util.UUID

data class NewPlanData(
    val title: String,
    val description: String,
    val startDate: LocalDate? = null,
    val endDate: LocalDate,
    val directorId: UUID,
    val document: AttachedDocument? = null
)