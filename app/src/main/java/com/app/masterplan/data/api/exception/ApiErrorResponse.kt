package com.app.masterplan.data.api.exception

import java.time.LocalDateTime

data class ApiErrorResponse(
    val status: Int,
    val message: String? = "",
    val timestamp: LocalDateTime,
)