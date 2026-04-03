package com.app.masterplan.data.api.userManagementApi.dto.responce

import java.time.LocalDateTime

data class UserErrorResponse(
    val status: Int,
    val message: String? = "",
    val timestamp: LocalDateTime,
)
