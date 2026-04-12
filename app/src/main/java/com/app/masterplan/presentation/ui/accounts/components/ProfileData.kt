package com.app.masterplan.presentation.ui.accounts.components

import com.app.masterplan.domain.model.userManagement.UserRole
import java.util.UUID

data class ProfileData(
    val login: String = "",
    val password: String = "",
    val roles: List<UserRole> = emptyList(),
    val name: String = "",
    val surname: String = "",
    val patronymic: String? = null,
    val directorId: UUID? =  null
)
