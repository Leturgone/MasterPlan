package com.app.masterplan.domain.model.reports

import java.time.LocalDateTime
import java.util.UUID

data class Report(
    val id: UUID,
    val title: String,
    val creationDate: LocalDateTime,
    val editDate: LocalDateTime? = null,
    val description: String? = null,
    val reportStatus: ReportStatus,
    val employeeId: UUID,
    val referenceId: UUID,
    val type: ReportType,
    val documentId: UUID
)