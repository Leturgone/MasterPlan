package com.app.masterplan.data.api.authApi.dto.responce

data class LoginResponse(
    val token: String,
    val type: String = "Bearer",
)


