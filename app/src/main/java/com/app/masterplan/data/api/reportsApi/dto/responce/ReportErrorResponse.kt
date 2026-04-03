package com.app.masterplan.data.api.reportsApi.dto.responce

import java.time.LocalDateTime

data class ReportErrorResponse(
    val status: Int,
    val message: String? = "",
    val timestamp: LocalDateTime,
)
