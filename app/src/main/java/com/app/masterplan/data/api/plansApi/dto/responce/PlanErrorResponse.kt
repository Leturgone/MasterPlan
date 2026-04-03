package com.app.masterplan.data.api.plansApi.dto.responce

import java.time.LocalDateTime

data class PlanErrorResponse(
    val status: Int,
    val message: String? = "",
    val timestamp: LocalDateTime,
)
