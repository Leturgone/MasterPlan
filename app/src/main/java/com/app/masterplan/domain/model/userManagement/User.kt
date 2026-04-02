package com.app.masterplan.domain.model.userManagement

import com.app.masterplan.domain.model.auth.UserRole
import java.util.UUID

data class User(
    val id: UUID,
    val login: String,
    val password: String,
    val roles: List<UserRole>
)