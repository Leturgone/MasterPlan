package com.app.masterplan.data.api.adminRequestsApi.dto.request

import java.util.UUID

data class CreateAdminRequestRequest(
    val id: UUID? = null,
    val title: String,
    val description: String
)
