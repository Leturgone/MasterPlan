package com.app.masterplan.data.api.reportsApi.dto.request

import java.util.UUID


data class CreateReportRequest(
    val id: UUID? = null,
    val title: String,
    val description: String? = null,
    val employeeId: UUID,
    val referenceId: UUID
)
