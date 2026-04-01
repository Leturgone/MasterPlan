package com.app.masterplan.domain.model.auth

import java.util.UUID

data class User(
    val id: UUID,
    val login: String,
    val password: String,
    val roles: List<AuthUserRole>
)
