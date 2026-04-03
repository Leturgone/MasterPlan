package com.app.masterplan.data.api.authApi.dto.responce

import java.time.LocalDateTime

data class AuthErrorResponse(
    val status: Int,
    val message: String? = "",
    val timestamp: LocalDateTime,
)