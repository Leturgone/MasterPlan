package com.app.masterplan.domain.dto

import java.util.UUID

data class NewReportData(
    val title: String,
    val description: String? = null,
    val employeeId: UUID,
    val referenceId: UUID,
)
