package com.app.masterplan.data.api.authApi.dto.responce

import java.util.UUID

data class LoginResponse(
    val token: String,
    val type: String = "Bearer",
    val roles: List<String>,
    val id: UUID
)


