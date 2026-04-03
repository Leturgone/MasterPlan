package com.app.masterplan.data.api.reportsApi.dto.request

import java.util.UUID

data class UpdateReportRequest(
    val title: String,
    val description: String?,
    val documentId: UUID
)
