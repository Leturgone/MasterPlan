package com.app.masterplan.domain.model.auth

data class JwtToken(
    val token: String,
    val authUserName: String,
    val roles: Set<AuthUserRole>
)