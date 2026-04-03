package com.app.masterplan.data.api.employeeApi.dto.responce

import java.time.LocalDateTime

data class EmployeeErrorResponse(
    val status: Int,
    val message: String? = "",
    val timestamp: LocalDateTime,
)