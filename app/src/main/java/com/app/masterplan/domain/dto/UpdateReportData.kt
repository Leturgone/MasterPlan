package com.app.masterplan.domain.dto

import com.app.masterplan.domain.model.reports.ReportType
import java.util.UUID

data class UpdateReportData(
    val title: String,
    val description: String? = null,
    val documentId: UUID
)
