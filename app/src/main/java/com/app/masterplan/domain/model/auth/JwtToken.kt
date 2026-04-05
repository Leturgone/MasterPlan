package com.app.masterplan.domain.model.auth

import com.app.masterplan.domain.model.userManagement.UserRole

data class JwtToken(
    val token: String,
    val roles: Set<UserRole>
)