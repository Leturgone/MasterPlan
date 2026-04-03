package com.app.masterplan.data.api.reportsApi.dto.responce

import java.time.LocalDateTime
import java.util.UUID


data class ReportResponse(
    val id: UUID,
    val title: String,
    val creationDate: LocalDateTime,
    val editDate: LocalDateTime? = null,
    val description: String? = null,
    val reportStatus: String,
    val employeeId: UUID,
    val referenceId: UUID,
    val type: String,
    val documentId: UUID
)
