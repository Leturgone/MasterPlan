package com.app.masterplan.domain.model.userManagement

import java.util.UUID

data class User(
    val id: UUID,
    val login: String,
    val password: String,
    val roles: List<UserRole>
)