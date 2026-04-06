package com.app.masterplan.data.api.adminRequestsApi.dto.responce

import java.util.UUID

data class AdminAnswerResponse(
    val id: UUID,
    val title: String,
    val description: String,
    val adminRequestId: UUID
)
