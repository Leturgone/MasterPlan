package com.app.masterplan.data.api.adminRequestsApi.dto.responce

import java.time.LocalDateTime

data class AdminRequestsErrorResponse(
    val status: Int,
    val message: String? = "",
    val timestamp: LocalDateTime,
)
