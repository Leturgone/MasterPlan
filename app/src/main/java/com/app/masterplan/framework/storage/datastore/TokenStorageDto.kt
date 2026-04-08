package com.app.masterplan.framework.storage.datastore

import com.app.masterplan.domain.model.userManagement.UserRole
import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class TokenStorageDto(
    val token: String,
    val roles: Set<UserRole>,
    val id: UUID
)