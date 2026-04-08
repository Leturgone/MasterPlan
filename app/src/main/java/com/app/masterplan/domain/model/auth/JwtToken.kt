package com.app.masterplan.domain.model.auth

import com.app.masterplan.domain.model.userManagement.UserRole
import java.util.UUID

data class JwtToken(
    val token: String,
    val roles: Set<UserRole>,
    val id: UUID
)