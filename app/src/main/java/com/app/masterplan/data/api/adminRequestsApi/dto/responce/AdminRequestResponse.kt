package com.app.masterplan.data.api.adminRequestsApi.dto.responce

import java.util.UUID


data class AdminRequestResponse(
    val id: UUID,
    val title: String,
    val description: String,
    val creationDate: String,
    val senderId: UUID,
    val status: String
)
